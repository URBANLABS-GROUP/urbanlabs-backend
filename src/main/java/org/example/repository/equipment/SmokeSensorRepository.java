package org.example.repository.equipment;

import org.example.model.iot.equipment.impl.smoke.SmokeSensor;
import org.example.model.iot.equipment.impl.temp.TempSensor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SmokeSensorRepository extends JpaRepository<SmokeSensor, Integer> {

}
