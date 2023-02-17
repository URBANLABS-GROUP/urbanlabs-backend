package org.example.repository;

import org.example.model.request.Request;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.Instant;
import java.util.List;

public interface RequestRepository extends JpaRepository<Request, Integer> {

    List<Request> findAllByRoomIdInAndCreateTimeGreaterThanEqualAndCreateTimeLessThanEqual(
        List<Integer> roomIds, Instant from, Instant to);
}
