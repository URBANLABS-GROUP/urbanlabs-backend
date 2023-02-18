package org.example.repository.equipment;

import org.example.model.iot.equipment.impl.powersocket.PowerSocket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PowerSocketRepository extends JpaRepository<PowerSocket, Integer> {

    List<PowerSocket> findAllByRoomIdIn(List<Integer> roomIds);


}
