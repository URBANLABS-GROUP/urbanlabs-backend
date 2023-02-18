package org.example.repository;

import org.example.model.document.Check;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.Instant;
import java.util.List;

public interface CheckRepository extends JpaRepository<Check, Integer> {

    List<Check> findAllByRoomIdInAndTimeGreaterThanEqualAndTimeLessThanEqual(
        List<Integer> roomIds, Instant from, Instant to
    );
}
