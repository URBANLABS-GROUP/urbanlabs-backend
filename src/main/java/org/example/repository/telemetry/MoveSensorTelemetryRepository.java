

package org.example.repository.telemetry;

import org.example.model.iot.equipment.EquipmentType;
import org.example.model.telemetry.impl.iot.MoveSensorTelemetry;
import org.example.model.telemetry.impl.iot.PowerSocketTelemetry;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.Instant;
import java.util.List;

public interface MoveSensorTelemetryRepository extends JpaRepository<MoveSensorTelemetry, Integer> {

    List<MoveSensorTelemetry> findAllByEquipmentTypeEqualsAndEquipmentIdInAndFixTimeGreaterThanEqualAndFixTimeLessThanEqualOrderByFixTimeAsc(
        EquipmentType equipmentType, List<Integer> equipmentId, Instant from, Instant to
    );
}
