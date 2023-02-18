package org.example.repository.equipment;

import org.example.model.iot.equipment.impl.move.MoveSensor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MoveSensorRepository extends JpaRepository<MoveSensor, Integer> {

    List<MoveSensor> findAllByRoomIdIn(List<Integer> roomIds);
}
