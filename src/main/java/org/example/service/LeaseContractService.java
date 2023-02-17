package org.example.service;

import org.example.model.document.LeaseContract;
import org.example.repository.LeaseContractRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Collections;
import java.util.List;

@Service
public class LeaseContractService {

    private LeaseContractRepository leaseContractRepository;

    public LeaseContractService(final LeaseContractRepository leaseContractRepository) {
        this.leaseContractRepository = leaseContractRepository;
    }

    public List<LeaseContract> getLeaseContractByRoomIdsIdTime(final List<Integer> roomIds, Instant from, Instant to) {
        if (to.isAfter(from)) {
            return Collections.emptyList();
        }
        return leaseContractRepository.findAllByRoomIdInAndStartTimeGreaterThanEqualAndEndTimeLessThanEqual(roomIds, from, to);
    }
}
