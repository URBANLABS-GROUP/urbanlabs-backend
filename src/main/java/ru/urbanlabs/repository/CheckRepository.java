package ru.urbanlabs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.urbanlabs.model.document.Check;

import java.time.Instant;
import java.util.List;

public interface CheckRepository extends JpaRepository<Check, Integer> {

    List<Check> findAllByRoomIdInAndTimeGreaterThanEqualAndTimeLessThanEqual(
        List<Integer> roomIds, Instant from, Instant to
    );
}
