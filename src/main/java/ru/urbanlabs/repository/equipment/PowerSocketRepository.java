package ru.urbanlabs.repository.equipment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.urbanlabs.model.iot.equipment.impl.powersocket.PowerSocket;

import java.util.List;

@Repository
public interface PowerSocketRepository extends JpaRepository<PowerSocket, Integer> {

    List<PowerSocket> findAllByRoomIdIn(List<Integer> roomIds);
}
