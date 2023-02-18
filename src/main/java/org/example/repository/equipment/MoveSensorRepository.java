package org.example.repository.equipment;

import org.example.model.iot.equipment.impl.move.MoveSensor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MoveSensorRepository extends JpaRepository<MoveSensor, Integer> {

    List<MoveSensor> findAllByRoomIdIn(List<Integer> roomIds);
}
