package org.example.controller;

import com.sun.istack.NotNull;
import org.example.dto.AnalyticDto;
import org.example.service.LeaseContractService;
import org.example.util.DateTimeUtils;
import org.example.util.Interval;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/analytics")
public class AnalyticsController {

    private final LeaseContractService leaseContractService;

    public AnalyticsController(final LeaseContractService leaseContractService) {
        this.leaseContractService = leaseContractService;
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
//        final List<Interval> intervals = Collections.singletonList(Interval.of(from, to)); // todo split by month
//        final List<Interval> intervals = DateTimeUtils.splitByDays(Interval.of(from, to), ZoneId.of("GMT+3"));
        final List<AnalyticDto> result = intervals.stream()
            .filter(interval -> interval.getFrom().isBefore(Instant.now()))
            .map(interval -> leaseContractService.buildAnalyticDto(businessCenterId, interval))
            .collect(Collectors.toList());

        return ResponseEntity.ok(result);
    }

}
