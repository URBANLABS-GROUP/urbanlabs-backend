package org.example.repository.equipment;

import org.example.model.iot.equipment.impl.powersocket.PowerSocket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PowerSocketRepository extends JpaRepository<PowerSocket, Integer> {

    List<PowerSocket> findAllByRoomIdIn(List<Integer> roomIds);
}
