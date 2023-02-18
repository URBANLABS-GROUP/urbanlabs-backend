package org.example.service;

import org.example.dao.DaoFactory;
import org.example.dto.RoomTelemetryInfo;
import org.example.model.businesscenter.Room;
import org.example.model.document.LeaseContract;
import org.example.model.iot.equipment.EquipmentType;
import org.example.model.iot.equipment.impl.powersocket.PowerSocket;
import org.example.model.iot.equipment.impl.temp.TempSensor;
import org.example.model.iot.equipment.impl.water.WaterSensor;
import org.example.model.telemetry.impl.iot.PowerSocketTelemetry;
import org.example.model.telemetry.impl.iot.TempSensorTelemetry;
import org.example.model.telemetry.impl.iot.WaterSensorTelemetry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.OptionalDouble;

@Service
public class RoomTelemetryService {

    private final DaoFactory daoFactory;

    @Autowired
    public RoomTelemetryService(DaoFactory daoFactory) {
        this.daoFactory = daoFactory;
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
                    daysPowerTelemetries.get(daysPowerTelemetries.size() - 1).getVatt() - daysPowerTelemetries.get(0).getVatt()
                );
            }

            final List<PowerSocketTelemetry> monthPowerTelemetries = daoFactory.getPowerSocketTelemetryRepository()
                .findAllByEquipmentTypeEqualsAndEquipmentIdInAndFixTimeGreaterThanEqualAndFixTimeLessThanEqualOrderByFixTimeAsc(EquipmentType.POWER_SOCKET, Collections.singletonList(powerSensor.getId()), now.minus(30, ChronoUnit.DAYS), now);
            if (!monthPowerTelemetries.isEmpty()) {
                roomTelemetryInfo.setAveragePowerConsumption(
                    monthPowerTelemetries.get(monthPowerTelemetries.size() - 1).getVatt() - monthPowerTelemetries.get(0).getVatt()
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
}
