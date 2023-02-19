package ru.urbanlabs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.urbanlabs.model.customer.Lessor;

public interface LessorRepository  extends JpaRepository<Lessor, Integer> {
}
