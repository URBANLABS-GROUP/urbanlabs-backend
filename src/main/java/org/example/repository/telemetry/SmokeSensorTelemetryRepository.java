

package org.example.repository.telemetry;

import org.example.model.iot.equipment.EquipmentType;
import org.example.model.telemetry.impl.iot.PowerSocketTelemetry;
import org.example.model.telemetry.impl.iot.SmokeSensorTelemetry;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.Instant;
import java.util.List;

public interface SmokeSensorTelemetryRepository extends JpaRepository<SmokeSensorTelemetry, Integer> {

    List<SmokeSensorTelemetry> findAllByEquipmentTypeEqualsAndEquipmentIdInAndFixTimeGreaterThanEqualAndFixTimeLessThanEqualOrderByFixTimeAsc(
        EquipmentType equipmentType, List<Integer> equipmentId, Instant from, Instant to
    );
}
