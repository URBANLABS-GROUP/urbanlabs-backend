package ru.urbanlabs.repository.equipment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.urbanlabs.model.iot.equipment.impl.move.MoveSensor;

import java.util.List;

@Repository
public interface MoveSensorRepository extends JpaRepository<MoveSensor, Integer> {

    List<MoveSensor> findAllByRoomIdIn(List<Integer> roomIds);
}
