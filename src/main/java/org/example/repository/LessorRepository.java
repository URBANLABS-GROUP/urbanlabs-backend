package org.example.repository;

import org.example.model.customer.Lessor;
import org.example.model.event.LeaseEvent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LessorRepository  extends JpaRepository<Lessor, Integer> {
}
