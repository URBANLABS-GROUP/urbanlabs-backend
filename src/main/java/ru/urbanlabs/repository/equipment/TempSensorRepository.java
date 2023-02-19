package ru.urbanlabs.repository.equipment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.urbanlabs.model.iot.equipment.impl.temp.TempSensor;

import java.util.List;

@Repository
public interface TempSensorRepository extends JpaRepository<TempSensor, Integer> {

    List<TempSensor> findAllByRoomIdIn(List<Integer> roomIds);
}
