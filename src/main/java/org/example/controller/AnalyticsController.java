package org.example.controller;

import com.sun.istack.NotNull;
import org.example.dto.AnalyticDto;
import org.example.model.businesscenter.BusinessCenter;
import org.example.model.businesscenter.BusinessCenterStorey;
import org.example.model.businesscenter.Room;
import org.example.model.document.LeaseContract;
import org.example.repository.BusinessCenterRepository;
import org.example.service.LeaseContractService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/analytics")
public class AnalyticsController {

    private final LeaseContractService leaseContractService;
    private final BusinessCenterRepository businessCenterRepository;

    public AnalyticsController(final LeaseContractService leaseContractService,
                               final BusinessCenterRepository businessCenterRepository) {
        this.leaseContractService = leaseContractService;
        this.businessCenterRepository = businessCenterRepository;
    }

    @RequestMapping(value = "/analyze/{businessCenterId}", method = RequestMethod.GET, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<AnalyticDto> analyze(
            @PathVariable("businessCenterId") final int businessCenterId,
            @RequestParam("from") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) @NotNull final ZonedDateTime from,
            @RequestParam("to") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) @NotNull final ZonedDateTime to
    ) {
        final Optional<BusinessCenter> businessCenter = businessCenterRepository.findById(businessCenterId);
        if (businessCenter.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        final AnalyticDto analyticDto = new AnalyticDto();

        final List<Integer> roomIds = businessCenter.get().getStoreys().stream()
                .map(BusinessCenterStorey::getRooms)
                .flatMap(Collection::stream)
                .map(Room::getId)
                .collect(Collectors.toList());

        final List<LeaseContract> leaseContractByTime = leaseContractService
                .getLeaseContractByRoomIdsIdTime(roomIds, from.toInstant(), to.toInstant());

        analyticDto.setRoomCount(roomIds.size());
        analyticDto.setRentCount(leaseContractByTime.size());


        return ResponseEntity.ok(analyticDto);
    }

}
