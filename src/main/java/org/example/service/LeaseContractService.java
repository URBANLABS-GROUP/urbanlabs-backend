package org.example.service;

import org.example.dto.AnalyticDto;
import org.example.model.businesscenter.BusinessCenter;
import org.example.model.businesscenter.BusinessCenterStorey;
import org.example.model.businesscenter.Room;
import org.example.model.document.LeaseContract;
import org.example.model.event.LeaseEvent;
import org.example.repository.BusinessCenterRepository;
import org.example.repository.LeaseContractRepository;
import org.example.repository.LeaseEventRepository;
import org.example.util.Interval;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class LeaseContractService {

    private LeaseContractRepository leaseContractRepository;
    private LeaseEventRepository leaseEventRepository;
    private final BusinessCenterRepository businessCenterRepository;

    public LeaseContractService(final LeaseContractRepository leaseContractRepository,
                                final LeaseEventRepository leaseEventRepository,
                                final BusinessCenterRepository businessCenterRepository) {
        this.leaseContractRepository = leaseContractRepository;
        this.leaseEventRepository = leaseEventRepository;
        this.businessCenterRepository = businessCenterRepository;
    }

    public List<LeaseContract> getLeaseContractByRoomIdsIdTime(final List<Integer> roomIds, Instant from, Instant to) {
        if (!to.isAfter(from)) {
            return Collections.emptyList();
        }
        return leaseContractRepository.findAllByRoomIdInAndStartTimeLessThanEqualAndEndTimeGreaterThanEqual(roomIds, from, to);
    }

    public Integer calcExpectedIncome(final List<LeaseContract> leaseContracts, final Instant now) {
        List<LeaseContract> leaseActiveOnTime = leaseContracts.stream()
                .filter(c -> new Interval(c.getStartTime(), c.getEndTime()).isInclude(now))
                .collect(Collectors.toList());

        return leaseActiveOnTime.stream()
                .map(LeaseContract::getRent)
                .mapToInt(Integer::intValue)
                .sum();
    }

    public Integer calcRealIncome(final List<LeaseContract> leaseContracts, final Instant from, final Instant to) {
        final List<Integer> contactIds = leaseContracts.stream()
                .map(LeaseContract::getId)
                .collect(Collectors.toList());

        final List<LeaseEvent> leaseEvents = leaseEventRepository.findAllByLeaseContractIdInAndPaymentMonthGreaterThanEqualAndPaymentMonthLessThanEqual(
                contactIds, from, to
        );

        return leaseEvents.stream()
                .map(LeaseEvent::getMoney)
                .mapToInt(Integer::intValue)
                .sum();
    }

    public AnalyticDto buildAnalyticDto(final Integer businessCenterId, final Interval interval) {
        final Optional<BusinessCenter> businessCenter = businessCenterRepository.findById(businessCenterId);
        if (businessCenter.isEmpty()) {
            throw new IllegalArgumentException("Cannot find business center with id = " + businessCenterId);
        }

        final AnalyticDto analyticDto = new AnalyticDto();

        final List<Integer> roomIds = businessCenter.get().getStoreys().stream()
                .map(BusinessCenterStorey::getRooms)
                .flatMap(Collection::stream)
                .map(Room::getId)
                .collect(Collectors.toList());

        final List<LeaseContract> leaseContractByTime = getLeaseContractByRoomIdsIdTime(roomIds,
               interval.getFrom(), interval.getTo());

        analyticDto.setDate(Date.from(interval.getFrom()));
        analyticDto.setRoomCount(roomIds.size());
        analyticDto.setRentCount(leaseContractByTime.size());

        analyticDto.setExpectedIncome(calcExpectedIncome(leaseContractByTime, interval.getFrom()));
        analyticDto.setRealIncome(calcRealIncome(leaseContractByTime, interval.getFrom(), interval.getTo()));

        return analyticDto;
    }
}
