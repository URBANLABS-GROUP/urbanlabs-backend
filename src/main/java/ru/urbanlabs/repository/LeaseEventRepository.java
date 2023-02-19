package ru.urbanlabs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.urbanlabs.model.event.LeaseEvent;

import java.time.Instant;
import java.util.List;

public interface LeaseEventRepository extends JpaRepository<LeaseEvent, Integer> {

    List<LeaseEvent> findAllByLeaseContractIdInAndPaymentMonthGreaterThanEqualAndPaymentMonthLessThanEqual(
            List<Integer> leaseContractIds, Instant from, Instant to);

}
