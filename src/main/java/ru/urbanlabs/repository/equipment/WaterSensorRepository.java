package ru.urbanlabs.repository.equipment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.urbanlabs.model.iot.equipment.impl.water.WaterSensor;

import java.util.List;

@Repository
public interface WaterSensorRepository extends JpaRepository<WaterSensor, Integer> {

    List<WaterSensor> findAllByRoomIdIn(List<Integer> roomIds);
}
