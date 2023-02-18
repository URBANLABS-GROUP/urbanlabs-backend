
package org.example.repository.telemetry;

import org.example.model.iot.equipment.EquipmentType;
import org.example.model.telemetry.impl.iot.PowerSocketTelemetry;
import org.example.model.telemetry.impl.iot.TempSensorTelemetry;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.Instant;
import java.util.List;

public interface TempSensorTelemetryRepository extends JpaRepository<TempSensorTelemetry, Integer> {

    List<TempSensorTelemetry> findAllByEquipmentTypeEqualsAndEquipmentIdInAndFixTimeGreaterThanEqualAndFixTimeLessThanEqualOrderByFixTimeAsc(
        EquipmentType equipmentType, List<Integer> equipmentId, Instant from, Instant to
    );
}
