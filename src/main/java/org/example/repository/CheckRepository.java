package org.example.repository;

import org.example.model.document.Check;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CheckRepository extends JpaRepository<Check, Integer> {
}
