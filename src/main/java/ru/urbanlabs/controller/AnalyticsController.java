package ru.urbanlabs.controller;

import com.sun.istack.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.urbanlabs.dao.DaoFactory;
import ru.urbanlabs.dto.AnalyticDto;
import ru.urbanlabs.dto.Plot;
import ru.urbanlabs.model.businesscenter.BusinessCenter;
import ru.urbanlabs.service.AnalyticsService;
import ru.urbanlabs.service.PlotService;
import ru.urbanlabs.util.DateTimeUtils;
import ru.urbanlabs.util.Interval;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/analytics")
public class AnalyticsController {

    private final AnalyticsService analyticsService;
    private final PlotService plotService;
    private final DaoFactory daoFactory;

    @Autowired
    public AnalyticsController(final AnalyticsService analyticsService,
                               final PlotService plotService,
                               final DaoFactory daoFactory) {
        this.analyticsService = analyticsService;
        this.plotService = plotService;
        this.daoFactory = daoFactory;
    }

    @RequestMapping(value = "/analyze/{businessCenterId}", method = RequestMethod.GET, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<List<AnalyticDto>> analyze(
        @PathVariable("businessCenterId") final int businessCenterId,
        @RequestParam("from") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) @NotNull final Instant from,
        @RequestParam("to") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) @NotNull final Instant to
    ) {

        final List<Interval> intervals = DateTimeUtils.splitByMonth(
            LocalDateTime.ofInstant(from, ZoneId.of("UTC")),
            LocalDateTime.ofInstant(to, ZoneId.of("UTC"))
        );
        final List<AnalyticDto> result = intervals.stream()
            .filter(interval -> interval.getFrom().isBefore(Instant.now()))
            .map(interval -> analyticsService.buildAnalyticDto(businessCenterId, interval))
            .collect(Collectors.toList());

        return ResponseEntity.ok(result);
    }

    @RequestMapping(value = "/plot/temp/{businessCenterId}", method = RequestMethod.GET, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Plot<Integer>>> plotTemp(
        @PathVariable("businessCenterId") final int businessCenterId,
        @RequestParam("from") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) @NotNull final Instant from,
        @RequestParam("to") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) @NotNull final Instant to
    ) {

        final Optional<BusinessCenter> businessCenter = daoFactory.getBusinessCenterRepository().findById(businessCenterId);
        if (businessCenter.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        final List<Interval> intervals = DateTimeUtils.splitByMonth(
            LocalDateTime.ofInstant(from, ZoneId.of("UTC")),
            LocalDateTime.ofInstant(to, ZoneId.of("UTC"))
        );
        final List<Plot<Integer>> result = intervals.stream()
            .filter(interval -> interval.getFrom().isBefore(Instant.now()))
            .map(interval -> plotService.buildTempPlot(businessCenter.get(), interval.getFrom(), interval.getTo()))
            .flatMap(Collection::stream)
            .collect(Collectors.toList());

        return ResponseEntity.ok(result);
    }

    @RequestMapping(value = "/plot/power/{businessCenterId}", method = RequestMethod.GET, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Plot<Integer>>> plotPower(
        @PathVariable("businessCenterId") final int businessCenterId,
        @RequestParam("from") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) @NotNull final Instant from,
        @RequestParam("to") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) @NotNull final Instant to
    ) {

        final Optional<BusinessCenter> businessCenter = daoFactory.getBusinessCenterRepository().findById(businessCenterId);
        if (businessCenter.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        final List<Interval> intervals = DateTimeUtils.splitByMonth(
            LocalDateTime.ofInstant(from, ZoneId.of("UTC")),
            LocalDateTime.ofInstant(to, ZoneId.of("UTC"))
        );
        final List<Plot<Integer>> result = intervals.stream()
            .filter(interval -> interval.getFrom().isBefore(Instant.now()))
            .map(interval -> plotService.buildPowerPlot(businessCenter.get(), interval.getFrom(), interval.getTo()))
            .flatMap(Collection::stream)
            .collect(Collectors.toList());

        return ResponseEntity.ok(result);
    }

}
