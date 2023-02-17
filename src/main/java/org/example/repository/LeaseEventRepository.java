package org.example.repository;

import org.example.model.event.LeaseEvent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.Instant;
import java.util.List;

public interface LeaseEventRepository extends JpaRepository<LeaseEvent, Integer> {

    List<LeaseEvent> findAllByLeaseContractIdInAndPaymentMonthGreaterThanEqualAndPaymentMonthLessThanEqual(
            List<Integer> leaseContractIds, Instant from, Instant to);

}
