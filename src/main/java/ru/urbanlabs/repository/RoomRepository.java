package ru.urbanlabs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.urbanlabs.model.businesscenter.Room;

public interface RoomRepository extends JpaRepository<Room, Integer> {
}
