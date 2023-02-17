package org.example.repository;

import org.example.model.document.LeaseContract;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.Instant;
import java.util.List;

public interface LeaseContractRepository extends JpaRepository<LeaseContract, Integer> {

    List<LeaseContract> findAllByStartTimeGreaterThanEqualAndEndTimeLessThanEqual(Instant from, Instant to);
    List<LeaseContract> findAllByLessorIdAndStartTimeGreaterThanEqualAndEndTimeLessThanEqual(Integer lessorId, Instant from, Instant to);
    List<LeaseContract> findAllByRoomIdInAndStartTimeGreaterThanEqualAndEndTimeLessThanEqual(final List<Integer> roomIds, Instant from, Instant to);
}
