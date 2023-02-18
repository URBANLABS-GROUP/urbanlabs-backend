
package org.example.repository.telemetry;

import org.example.model.iot.equipment.EquipmentType;
import org.example.model.telemetry.impl.iot.PowerSocketTelemetry;
import org.example.model.telemetry.impl.iot.TempSensorTelemetry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

@Repository
public interface PowerSocketTelemetryRepository extends JpaRepository<PowerSocketTelemetry, Integer> {

    List<PowerSocketTelemetry> findAllByEquipmentTypeEqualsAndEquipmentIdInAndFixTimeGreaterThanEqualAndFixTimeLessThanEqualOrderByFixTimeAsc(
        EquipmentType equipmentType, List<Integer> equipmentId, Instant from, Instant to
    );


    PowerSocketTelemetry findLastByEquipmentTypeEqualsAndEquipmentIdEquals(EquipmentType equipmentType, Integer equipmentId);
}
