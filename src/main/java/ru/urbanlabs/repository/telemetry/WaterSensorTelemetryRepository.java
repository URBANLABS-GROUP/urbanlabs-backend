
package ru.urbanlabs.repository.telemetry;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.urbanlabs.model.iot.equipment.EquipmentType;
import ru.urbanlabs.model.telemetry.impl.iot.WaterSensorTelemetry;

import java.time.Instant;
import java.util.List;

@Repository
public interface WaterSensorTelemetryRepository extends JpaRepository<WaterSensorTelemetry, Integer> {

    List<WaterSensorTelemetry> findAllByEquipmentTypeEqualsAndEquipmentIdInAndFixTimeGreaterThanEqualAndFixTimeLessThanEqualOrderByFixTimeAsc(
        EquipmentType equipmentType, List<Integer> equipmentId, Instant from, Instant to
    );
}
