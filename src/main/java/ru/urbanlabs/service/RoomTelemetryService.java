package ru.urbanlabs.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.urbanlabs.dao.DaoFactory;
import ru.urbanlabs.dto.RoomTelemetryInfo;
import ru.urbanlabs.model.businesscenter.BusinessCenter;
import ru.urbanlabs.model.businesscenter.BusinessCenterStorey;
import ru.urbanlabs.model.businesscenter.Room;
import ru.urbanlabs.model.document.LeaseContract;
import ru.urbanlabs.model.iot.equipment.EquipmentType;
import ru.urbanlabs.model.iot.equipment.impl.powersocket.PowerSocket;
import ru.urbanlabs.model.iot.equipment.impl.temp.TempSensor;
import ru.urbanlabs.model.iot.equipment.impl.water.WaterSensor;
import ru.urbanlabs.model.telemetry.impl.iot.PowerSocketTelemetry;
import ru.urbanlabs.model.telemetry.impl.iot.TempSensorTelemetry;
import ru.urbanlabs.model.telemetry.impl.iot.WaterSensorTelemetry;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Service
public class RoomTelemetryService {

    private final DaoFactory daoFactory;
    private final AnalyticsService analyticsService;

    @Autowired
    public RoomTelemetryService(final DaoFactory daoFactory,
                                final AnalyticsService analyticsService) {
        this.daoFactory = daoFactory;
        this.analyticsService = analyticsService;
    }

    public RoomTelemetryInfo buildRoomTelemetryInfo(Room room) {
        final Instant now = Instant.now();

        RoomTelemetryInfo roomTelemetryInfo = new RoomTelemetryInfo();

        if (room.getLeaseContractId() != null) {
            Optional<LeaseContract> leaseContract = daoFactory.getLeaseContractRepository().findById(room.getLeaseContractId());
            if (leaseContract.isPresent()) {
                roomTelemetryInfo.setRent(leaseContract.get().getRent());
            }
        }

        Optional<BusinessCenterStorey> businessCenterStorey = daoFactory.getBusinessCenterStoreyRepository().findById(
            room.getBusinessCenterStoreyId());
        if (businessCenterStorey.isPresent()) {
            Optional<BusinessCenter> businessCenter = daoFactory.getBusinessCenterRepository().findById(
                businessCenterStorey.get().getBusinessCenterId());
            if (businessCenter.isPresent()) {
                int totalExpenses = analyticsService.buildTotalExpenses(businessCenter.get(), room,
                    now.minus(30, ChronoUnit.DAYS), now);

                roomTelemetryInfo.setExpenses(totalExpenses);
            }
        }

        final List<TempSensor> tempSensors = daoFactory.getTempSensorRepository().findAllByRoomIdIn(Collections.singletonList(room.getId()));
        if (!tempSensors.isEmpty()) {
            final TempSensor tempSensor = tempSensors.get(0);
            final TempSensorTelemetry lastTempTelemetry = daoFactory.getTempSensorTelemetryRepository()
                .findTopByEquipmentTypeEqualsAndEquipmentIdEqualsOrderByFixTimeAsc(EquipmentType.TEMP, tempSensor.getId());
            roomTelemetryInfo.setCurTemp(lastTempTelemetry.getTemp());

            final List<TempSensorTelemetry> telemetries = daoFactory.getTempSensorTelemetryRepository()
                .findAllByEquipmentTypeEqualsAndEquipmentIdInAndFixTimeGreaterThanEqualAndFixTimeLessThanEqualOrderByFixTimeAsc
                    (EquipmentType.TEMP, Collections.singletonList(tempSensor.getId()), now.minus(30, ChronoUnit.DAYS), now);

            final OptionalDouble monthAverageTemp = telemetries.stream()
                .map(TempSensorTelemetry::getTemp)
                .mapToInt(Integer::intValue)
                .average();
            if (monthAverageTemp.isPresent()) {
                roomTelemetryInfo.setAverageCurTemp((int) monthAverageTemp.getAsDouble());
            }
        }

        final List<PowerSocket> powerSensors = daoFactory.getPowerSocketRepository().findAllByRoomIdIn(Collections.singletonList(room.getId()));
        for (final PowerSocket powerSensor : powerSensors) {
            final List<PowerSocketTelemetry> daysPowerTelemetries = daoFactory.getPowerSocketTelemetryRepository()
                .findAllByEquipmentTypeEqualsAndEquipmentIdInAndFixTimeGreaterThanEqualAndFixTimeLessThanEqualOrderByFixTimeAsc(EquipmentType.POWER_SOCKET, Collections.singletonList(powerSensor.getId()), now.minus(1, ChronoUnit.DAYS), now);

            if (!daysPowerTelemetries.isEmpty()) {
                roomTelemetryInfo.setCurDayPowerConsumption(
                    daysPowerTelemetries.get(daysPowerTelemetries.size() - 1).getWatt() - daysPowerTelemetries.get(0).getWatt()
                );
            }

            final List<PowerSocketTelemetry> monthPowerTelemetries = daoFactory.getPowerSocketTelemetryRepository()
                .findAllByEquipmentTypeEqualsAndEquipmentIdInAndFixTimeGreaterThanEqualAndFixTimeLessThanEqualOrderByFixTimeAsc(EquipmentType.POWER_SOCKET, Collections.singletonList(powerSensor.getId()), now.minus(30, ChronoUnit.DAYS), now);
            if (!monthPowerTelemetries.isEmpty()) {
                roomTelemetryInfo.setAveragePowerConsumption(
                    monthPowerTelemetries.get(monthPowerTelemetries.size() - 1).getWatt() - monthPowerTelemetries.get(0).getWatt()
                );
            }
        }

        final List<WaterSensor> waterSensors = daoFactory.getWaterSensorRepository().findAllByRoomIdIn(Collections.singletonList(room.getId()));
        for (final WaterSensor waterSensor : waterSensors) {
            final List<WaterSensorTelemetry> daysPowerTelemetries = daoFactory.getWaterSensorTelemetryRepository()
                .findAllByEquipmentTypeEqualsAndEquipmentIdInAndFixTimeGreaterThanEqualAndFixTimeLessThanEqualOrderByFixTimeAsc(EquipmentType.WATER_SENSOR, Collections.singletonList(waterSensor.getId()), now.minus(1, ChronoUnit.DAYS), now);

            if (!daysPowerTelemetries.isEmpty()) {
                roomTelemetryInfo.setCurDayWaterConsumption(
                    daysPowerTelemetries.get(daysPowerTelemetries.size() - 1).getWaterVolume() - daysPowerTelemetries.get(0).getWaterVolume()
                );
            }

            final List<WaterSensorTelemetry> monthPowerTelemetries = daoFactory.getWaterSensorTelemetryRepository()
                .findAllByEquipmentTypeEqualsAndEquipmentIdInAndFixTimeGreaterThanEqualAndFixTimeLessThanEqualOrderByFixTimeAsc(EquipmentType.WATER_SENSOR, Collections.singletonList(waterSensor.getId()), now.minus(30, ChronoUnit.DAYS), now);
            if (!monthPowerTelemetries.isEmpty()) {
                roomTelemetryInfo.setAverageWaterConsumption(
                    monthPowerTelemetries.get(monthPowerTelemetries.size() - 1).getWaterVolume() - monthPowerTelemetries.get(0).getWaterVolume()
                );
            }
        }

        return roomTelemetryInfo;
    }

    public void randomizeRoomTelemetryInfo(RoomTelemetryInfo roomTelemetryInfo) {
        Random r = new Random();
        int low = 0;
        int high = 13;
        int result = r.nextInt(high - low) + low;
        if (roomTelemetryInfo.getCurTemp() != null) {
            roomTelemetryInfo.setCurTemp(roomTelemetryInfo.getCurTemp() + result);
        }
        if (roomTelemetryInfo.getCurDayPowerConsumption() != null) {
            int result1 = r.nextInt(50 - 10) + 10;
            roomTelemetryInfo.setCurDayPowerConsumption(roomTelemetryInfo.getCurDayPowerConsumption() + result1);
        }
        if (roomTelemetryInfo.getCurDayWaterConsumption() != null) {
            int result2 = r.nextInt(60 - 5) + 5;
            roomTelemetryInfo.setCurDayWaterConsumption(roomTelemetryInfo.getCurDayWaterConsumption() + result2);
        }
    }
}
