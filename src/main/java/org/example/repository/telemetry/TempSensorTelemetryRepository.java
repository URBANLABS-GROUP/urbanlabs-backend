
package org.example.repository.telemetry;

import org.example.model.iot.equipment.EquipmentType;
import org.example.model.telemetry.impl.iot.TempSensorTelemetry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

@Repository
public interface TempSensorTelemetryRepository extends JpaRepository<TempSensorTelemetry, Integer> {

    List<TempSensorTelemetry> findAllByEquipmentTypeEqualsAndEquipmentIdInAndFixTimeGreaterThanEqualAndFixTimeLessThanEqualOrderByFixTimeAsc(
        EquipmentType equipmentType, List<Integer> equipmentId, Instant from, Instant to
    );

    TempSensorTelemetry findTopByEquipmentTypeEqualsAndEquipmentIdEqualsOrderByFixTimeAsc(EquipmentType equipmentType, Integer equipmentId);
}
