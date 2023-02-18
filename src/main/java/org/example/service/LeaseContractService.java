package org.example.service;

import org.example.dto.AnalyticDto;
import org.example.model.businesscenter.BusinessCenter;
import org.example.model.businesscenter.BusinessCenterStorey;
import org.example.model.businesscenter.Room;
import org.example.model.document.LeaseContract;
import org.example.model.event.LeaseEvent;
import org.example.model.iot.equipment.EquipmentType;
import org.example.model.iot.equipment.IotEquipment;
import org.example.model.iot.equipment.impl.powersocket.PowerSocket;
import org.example.model.request.Request;
import org.example.model.telemetry.BaseTelemetry;
import org.example.model.telemetry.impl.iot.PowerSocketTelemetry;
import org.example.repository.*;
import org.example.repository.equipment.PowerSocketRepository;
import org.example.repository.telemetry.PowerSocketTelemetryRepository;
import org.example.util.Interval;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class LeaseContractService {

    private final LeaseContractRepository leaseContractRepository;
    private final LeaseEventRepository leaseEventRepository;
    private final BusinessCenterRepository businessCenterRepository;
    private final RequestRepository requestRepository;
    private final PowerSocketRepository powerSocketRepository;
    private final PowerSocketTelemetryRepository powerSocketTelemetryRepository;

    public LeaseContractService(final LeaseContractRepository leaseContractRepository,
                                final LeaseEventRepository leaseEventRepository,
                                final BusinessCenterRepository businessCenterRepository,
                                final RequestRepository requestRepository,
                                final PowerSocketRepository powerSocketRepository,
                                final PowerSocketTelemetryRepository powerSocketTelemetryRepository) {
        this.leaseContractRepository = leaseContractRepository;
        this.leaseEventRepository = leaseEventRepository;
        this.businessCenterRepository = businessCenterRepository;
        this.requestRepository = requestRepository;
        this.powerSocketRepository = powerSocketRepository;
        this.powerSocketTelemetryRepository = powerSocketTelemetryRepository;
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

    public Integer calcRequestExpenses(final List<Integer> roomIds, final Instant from, final Instant to) {
        final List<Request> requests = requestRepository.findAllByRoomIdInAndCreateTimeGreaterThanEqualAndCreateTimeLessThanEqual(roomIds, from, to);

        return requests.stream()
            .map(Request::getCost)
            .mapToInt(Integer::intValue)
            .sum();
    }

    public Integer calcPowerConsumption(final BusinessCenter businessCenter, final List<Integer> roomIds, final Instant from, final Instant to) {
        final List<PowerSocket> powerSockets = powerSocketRepository.findAllByRoomIdIn(roomIds);
        final List<PowerSocketTelemetry> telemetry = powerSocketTelemetryRepository.findAllByEquipmentTypeEqualsAndEquipmentIdInAndFixTimeGreaterThanEqualAndFixTimeLessThanEqualOrderByFixTimeAsc(
            EquipmentType.POWER_SOCKET, powerSockets.stream().map(IotEquipment::getId)
                .collect(Collectors.toList()), from, to);

        if (telemetry.isEmpty()) {
            return 0;
        }

        final Map<Integer, List<PowerSocketTelemetry>> collect = telemetry.stream()
            .collect(Collectors.groupingBy(BaseTelemetry::getEquipmentId));

        int montlyPowerConsumption = 0;
        for (final Map.Entry<Integer, List<PowerSocketTelemetry>> entry : collect.entrySet()) {
            montlyPowerConsumption += entry.getValue().get(entry.getValue().size() - 1).getVatt() -
                entry.getValue().get(0).getVatt();
        }

        return montlyPowerConsumption * businessCenter.getVattPrice();
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
        analyticDto.setRequestExpenses(calcRequestExpenses(roomIds, interval.getFrom(), interval.getTo()));
        analyticDto.setPowerConsumption(calcPowerConsumption(businessCenter.get(), roomIds, interval.getFrom(), interval.getTo()));

        return analyticDto;
    }
}
