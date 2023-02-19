package ru.urbanlabs.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.urbanlabs.dao.DaoFactory;
import ru.urbanlabs.dto.AnalyticDto;
import ru.urbanlabs.dto.alert.Alert;
import ru.urbanlabs.dto.alert.impl.RentWithNegativeProfitAlert;
import ru.urbanlabs.dto.alert.impl.TooHotTempAlert;
import ru.urbanlabs.dto.alert.impl.TooMuchPowerConsumptionAlert;
import ru.urbanlabs.model.businesscenter.BusinessCenter;
import ru.urbanlabs.model.businesscenter.BusinessCenterStorey;
import ru.urbanlabs.model.businesscenter.Room;
import ru.urbanlabs.model.document.Check;
import ru.urbanlabs.model.document.LeaseContract;
import ru.urbanlabs.model.event.LeaseEvent;
import ru.urbanlabs.model.iot.equipment.EquipmentType;
import ru.urbanlabs.model.iot.equipment.IotEquipment;
import ru.urbanlabs.model.iot.equipment.impl.powersocket.PowerSocket;
import ru.urbanlabs.model.iot.equipment.impl.temp.TempSensor;
import ru.urbanlabs.model.request.Request;
import ru.urbanlabs.model.telemetry.BaseTelemetry;
import ru.urbanlabs.model.telemetry.impl.iot.PowerSocketTelemetry;
import ru.urbanlabs.model.telemetry.impl.iot.TempSensorTelemetry;
import ru.urbanlabs.util.Interval;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class AnalyticsService {

    private final DaoFactory daoFactory;

    @Autowired
    public AnalyticsService(final DaoFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    public AnalyticDto buildAnalyticDto(final Integer businessCenterId, final Interval interval) {
        final Optional<BusinessCenter> businessCenter = daoFactory.getBusinessCenterRepository().findById(businessCenterId);
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
        analyticDto.setCheckExpenses(calcCheckExpenses(roomIds, interval.getFrom(), interval.getTo()));
        analyticDto.setPowerExpenses(calcPowerExpensive(businessCenter.get(), roomIds, interval.getFrom(), interval.getTo()));

        final List<Alert> alerts = new ArrayList<>();
        alerts.addAll(buildRentWithNegativeProfit(roomIds, interval.getFrom(), interval.getTo()));
        alerts.addAll(buildTooHotTempAlert(roomIds, interval.getFrom(), interval.getTo()));
        alerts.addAll(buildTooMuchPowerConsumptionAlert(roomIds, interval.getFrom(), interval.getTo()));

        analyticDto.setAlerts(alerts);
        return analyticDto;
    }

    private List<TooMuchPowerConsumptionAlert> buildTooMuchPowerConsumptionAlert(final List<Integer> roomIds,
                                                                                 final Instant from,
                                                                                 final Instant to) {
        if (!to.isAfter(from)) {
            return Collections.emptyList();
        }

        List<TooMuchPowerConsumptionAlert> result = new ArrayList<>();

        List<Room> rooms = daoFactory.getRoomRepository().findAllById(roomIds);
        for (final Room room : rooms) {
            if (room.getLeaseContractId() == null) {
                continue;
            }
            final Optional<LeaseContract> leaseContract = daoFactory.getLeaseContractRepository().findById(room.getLeaseContractId());
            if (leaseContract.isEmpty()) {
                continue;
            }
            final Integer powerConsumption = calcPowerConsumption(Collections.singletonList(room.getId()), from, to);

            if (room.getAllowablePowerConsumption() < powerConsumption) {
                result.add(new TooMuchPowerConsumptionAlert(room.getId(), powerConsumption, room.getAllowablePowerConsumption()));
            }
        }

        return result;
    }

    private List<RentWithNegativeProfitAlert> buildRentWithNegativeProfit(final List<Integer> roomIds,
                                                                          final Instant from,
                                                                          final Instant to) {
        if (!to.isAfter(from)) {
            return Collections.emptyList();
        }

        List<RentWithNegativeProfitAlert> result = new ArrayList<>();

        List<Room> rooms = daoFactory.getRoomRepository().findAllById(roomIds);
        for (final Room room : rooms) {
            if (room.getLeaseContractId() == null) {
                continue;
            }
            final Optional<LeaseContract> leaseContract = daoFactory.getLeaseContractRepository().findById(room.getLeaseContractId());
            if (leaseContract.isEmpty()) {
                continue;
            }
            final Optional<BusinessCenterStorey> businessCenterStorey = daoFactory.getBusinessCenterStoreyRepository().findById(room.getBusinessCenterStoreyId());
            final Optional<BusinessCenter> businessCenter = daoFactory.getBusinessCenterRepository().findById(businessCenterStorey.get().getBusinessCenterId());
            int totalExpenses = buildTotalExpenses(businessCenter.get(), room, from, to);
            if (leaseContract.get().getRent() < totalExpenses) {
                result.add(new RentWithNegativeProfitAlert(room.getId(), leaseContract.get().getRent(), totalExpenses));
            }
        }

        return result;
    }

    public int buildTotalExpenses(BusinessCenter businessCenter, Room room, Instant from, Instant to) {
        final Integer powerExpenses = Optional.ofNullable(calcPowerExpensive(businessCenter, Collections.singletonList(room.getId()), from, to)).orElse(0);
        final Integer requestExpenses = calcRequestExpenses(Collections.singletonList(room.getId()), from, to);
        final Integer checkExpenses = calcCheckExpenses(Collections.singletonList(room.getId()), from, to);

        int totalExpenses = powerExpenses + requestExpenses + checkExpenses; // todo water sensor
        return totalExpenses;
    }

    private List<TooHotTempAlert> buildTooHotTempAlert(final List<Integer> roomIds,
                                                       final Instant from,
                                                       final Instant to) {
        if (!to.isAfter(from)) {
            return Collections.emptyList();
        }

        List<TooHotTempAlert> result = new ArrayList<>();

        List<Room> rooms = daoFactory.getRoomRepository().findAllById(roomIds);
        for (final Room room : rooms) {
            if (room.getLeaseContractId() == null) {
                continue;
            }
            final Optional<LeaseContract> leaseContract = daoFactory.getLeaseContractRepository().findById(room.getLeaseContractId());
            if (leaseContract.isEmpty()) {
                continue;
            }
            final Optional<BusinessCenterStorey> businessCenterStorey = daoFactory.getBusinessCenterStoreyRepository().findById(room.getBusinessCenterStoreyId());
            final Optional<BusinessCenter> businessCenter = daoFactory.getBusinessCenterRepository().findById(businessCenterStorey.get().getBusinessCenterId());

            List<TempSensor> allByRoomIdIn = daoFactory.getTempSensorRepository().findAllByRoomIdIn(Collections.singletonList(room.getId()));

            for (TempSensor tempSensor : allByRoomIdIn) {
                List<TempSensorTelemetry> telemetries = daoFactory.getTempSensorTelemetryRepository()
                    .findAllByEquipmentTypeEqualsAndEquipmentIdInAndFixTimeGreaterThanEqualAndFixTimeLessThanEqualOrderByFixTimeAsc
                        (EquipmentType.TEMP, Collections.singletonList(tempSensor.getId()), from, to);

                if (telemetries.isEmpty()) {
                    continue;
                }

                final Double averageTemp = telemetries.stream()
                    .map(TempSensorTelemetry::getTemp)
                    .mapToInt(Integer::intValue)
                    .average()
                    .getAsDouble();

                if (room.getRequiredTemp() < averageTemp) {
                    result.add(new TooHotTempAlert(room.getId(), averageTemp.intValue(), room.getRequiredTemp()));
                }
            }

        }

        return result;
    }

    private List<LeaseContract> getLeaseContractByRoomIdsIdTime(final List<Integer> roomIds, Instant from, Instant to) {
        if (!to.isAfter(from)) {
            return Collections.emptyList();
        }
        return daoFactory.getLeaseContractRepository().findAllByRoomIdInAndStartTimeLessThanEqualAndEndTimeGreaterThanEqual(roomIds, from, to);
    }

    private Integer calcExpectedIncome(final List<LeaseContract> leaseContracts, final Instant now) {
        List<LeaseContract> leaseActiveOnTime = leaseContracts.stream()
            .filter(c -> new Interval(c.getStartTime(), c.getEndTime()).isInclude(now))
            .collect(Collectors.toList());

        return leaseActiveOnTime.stream()
            .map(LeaseContract::getRent)
            .mapToInt(Integer::intValue)
            .sum();
    }

    private Integer calcRealIncome(final List<LeaseContract> leaseContracts, final Instant from, final Instant to) {
        final List<Integer> contactIds = leaseContracts.stream()
            .map(LeaseContract::getId)
            .collect(Collectors.toList());

        final List<LeaseEvent> leaseEvents = daoFactory.getLeaseEventRepository().findAllByLeaseContractIdInAndPaymentMonthGreaterThanEqualAndPaymentMonthLessThanEqual(
            contactIds, from, to.minus(1, ChronoUnit.SECONDS)
        );

        return leaseEvents.stream()
            .map(LeaseEvent::getMoney)
            .mapToInt(Integer::intValue)
            .sum();
    }

    private Integer calcCheckExpenses(final List<Integer> roomIds, final Instant from, final Instant to) {
        final List<Check> checks = daoFactory.getCheckRepository().findAllByRoomIdInAndTimeGreaterThanEqualAndTimeLessThanEqual(
            roomIds, from, to);
        return checks.stream()
            .map(Check::getCost)
            .mapToInt(Integer::intValue)
            .sum();
    }

    private Integer calcRequestExpenses(final List<Integer> roomIds, final Instant from, final Instant to) {
        final List<Request> requests = daoFactory.getRequestRepository().findAllByRoomIdInAndCreateTimeGreaterThanEqualAndCreateTimeLessThanEqual(roomIds, from, to);

        return requests.stream()
            .map(Request::getCost)
            .mapToInt(Integer::intValue)
            .sum();
    }

    private Integer calcPowerExpensive(final BusinessCenter businessCenter, final List<Integer> roomIds, final Instant from, final Instant to) {
        if (businessCenter.getWattPrice() == null) {
            return null;
        }

        return calcPowerConsumption(roomIds, from, to) * businessCenter.getWattPrice();
    }

    private Integer calcPowerConsumption(final List<Integer> roomIds, final Instant from, final Instant to) {
        final List<PowerSocket> powerSockets = daoFactory.getPowerSocketRepository().findAllByRoomIdIn(roomIds);
        final List<PowerSocketTelemetry> telemetry = daoFactory.getPowerSocketTelemetryRepository().findAllByEquipmentTypeEqualsAndEquipmentIdInAndFixTimeGreaterThanEqualAndFixTimeLessThanEqualOrderByFixTimeAsc(
            EquipmentType.POWER_SOCKET, powerSockets.stream().map(IotEquipment::getId)
                .collect(Collectors.toList()), from, to);

        if (telemetry.isEmpty()) {
            return 0;
        }

        final Map<Integer, List<PowerSocketTelemetry>> collect = telemetry.stream()
            .collect(Collectors.groupingBy(BaseTelemetry::getEquipmentId));

        int montlyPowerConsumption = 0;
        for (final Map.Entry<Integer, List<PowerSocketTelemetry>> entry : collect.entrySet()) {
            montlyPowerConsumption += entry.getValue().get(entry.getValue().size() - 1).getWatt() -
                entry.getValue().get(0).getWatt();
        }

        return montlyPowerConsumption;
    }
}
