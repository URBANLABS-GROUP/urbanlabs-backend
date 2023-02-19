package ru.urbanlabs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.urbanlabs.model.document.LeaseContract;

import java.time.Instant;
import java.util.List;

public interface LeaseContractRepository extends JpaRepository<LeaseContract, Integer> {

    List<LeaseContract> findAllByStartTimeGreaterThanEqualAndEndTimeLessThanEqual(Instant from, Instant to);
    List<LeaseContract> findAllByLessorIdAndStartTimeGreaterThanEqualAndEndTimeLessThanEqual(Integer lessorId, Instant from, Instant to);
    List<LeaseContract> findAllByRoomIdInAndStartTimeLessThanEqualAndEndTimeGreaterThanEqual(final List<Integer> roomIds, Instant from, Instant to);
}
