

package ru.urbanlabs.repository.telemetry;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.urbanlabs.model.iot.equipment.EquipmentType;
import ru.urbanlabs.model.telemetry.impl.iot.MoveSensorTelemetry;

import java.time.Instant;
import java.util.List;

@Repository
public interface MoveSensorTelemetryRepository extends JpaRepository<MoveSensorTelemetry, Integer> {

    List<MoveSensorTelemetry> findAllByEquipmentTypeEqualsAndEquipmentIdInAndFixTimeGreaterThanEqualAndFixTimeLessThanEqualOrderByFixTimeAsc(
        EquipmentType equipmentType, List<Integer> equipmentId, Instant from, Instant to
    );
}
