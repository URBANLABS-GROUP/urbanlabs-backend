
package ru.urbanlabs.repository.telemetry;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.urbanlabs.model.iot.equipment.EquipmentType;
import ru.urbanlabs.model.telemetry.impl.iot.PowerSocketTelemetry;

import java.time.Instant;
import java.util.List;

@Repository
public interface PowerSocketTelemetryRepository extends JpaRepository<PowerSocketTelemetry, Integer> {

    List<PowerSocketTelemetry> findAllByEquipmentTypeEqualsAndEquipmentIdInAndFixTimeGreaterThanEqualAndFixTimeLessThanEqualOrderByFixTimeAsc(
        EquipmentType equipmentType, List<Integer> equipmentId, Instant from, Instant to
    );

    PowerSocketTelemetry findLastByEquipmentTypeEqualsAndEquipmentIdEquals(EquipmentType equipmentType, Integer equipmentId);
}
