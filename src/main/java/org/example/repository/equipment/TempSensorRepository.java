package org.example.repository.equipment;

import org.example.model.iot.equipment.impl.temp.TempSensor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TempSensorRepository extends JpaRepository<TempSensor, Integer> {

    List<TempSensor> findAllByRoomIdIn(List<Integer> roomIds);
}
