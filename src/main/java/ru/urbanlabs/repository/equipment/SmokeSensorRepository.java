package ru.urbanlabs.repository.equipment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.urbanlabs.model.iot.equipment.impl.smoke.SmokeSensor;

@Repository
public interface SmokeSensorRepository extends JpaRepository<SmokeSensor, Integer> {

}
