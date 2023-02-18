package org.example.repository.equipment;

import org.example.model.iot.equipment.impl.water.WaterSensor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WaterSensorRepository extends JpaRepository<WaterSensor, Integer> {

    List<WaterSensor> findAllByRoomIdIn(List<Integer> roomIds);
}
