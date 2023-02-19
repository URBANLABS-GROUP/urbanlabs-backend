
package ru.urbanlabs.repository.telemetry;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.urbanlabs.model.iot.equipment.EquipmentType;
import ru.urbanlabs.model.telemetry.impl.iot.TempSensorTelemetry;

import java.time.Instant;
import java.util.List;

@Repository
public interface TempSensorTelemetryRepository extends JpaRepository<TempSensorTelemetry, Integer> {

    List<TempSensorTelemetry> findAllByEquipmentTypeEqualsAndEquipmentIdInAndFixTimeGreaterThanEqualAndFixTimeLessThanEqualOrderByFixTimeAsc(
            EquipmentType equipmentType, List<Integer> equipmentId, Instant from, Instant to
    );

    TempSensorTelemetry findTopByEquipmentTypeEqualsAndEquipmentIdEqualsOrderByFixTimeAsc(EquipmentType equipmentType, Integer equipmentId);
}
