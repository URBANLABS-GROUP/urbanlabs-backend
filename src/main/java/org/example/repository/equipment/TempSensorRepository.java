package org.example.repository.equipment;

import org.example.model.event.LeaseEvent;
import org.example.model.iot.equipment.impl.temp.TempSensor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TempSensorRepository extends JpaRepository<TempSensor, Integer> {

    List<TempSensor> findAllByRoomIdIn(List<Integer> roomIds);
}
