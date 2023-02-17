package org.example.repository;

import org.example.model.customer.Lessee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LesseeRepository extends JpaRepository<Lessee, Integer> {
}
