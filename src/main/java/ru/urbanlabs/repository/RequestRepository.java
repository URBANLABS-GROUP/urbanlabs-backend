package ru.urbanlabs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.urbanlabs.model.request.Request;

import java.time.Instant;
import java.util.List;

public interface RequestRepository extends JpaRepository<Request, Integer> {

    List<Request> findAllByRoomIdInAndCreateTimeGreaterThanEqualAndCreateTimeLessThanEqual(
        List<Integer> roomIds, Instant from, Instant to);
}
