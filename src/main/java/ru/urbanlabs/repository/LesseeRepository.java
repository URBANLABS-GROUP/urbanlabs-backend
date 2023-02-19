package ru.urbanlabs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.urbanlabs.model.customer.Lessee;

public interface LesseeRepository extends JpaRepository<Lessee, Integer> {
}
