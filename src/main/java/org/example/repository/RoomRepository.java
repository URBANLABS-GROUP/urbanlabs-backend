package org.example.repository;

import org.example.model.businesscenter.BusinessCenter;
import org.example.model.businesscenter.Room;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository<Room, Integer> {
}
