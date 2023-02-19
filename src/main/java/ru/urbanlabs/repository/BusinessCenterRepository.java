package ru.urbanlabs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.urbanlabs.model.businesscenter.BusinessCenter;

public interface BusinessCenterRepository extends JpaRepository<BusinessCenter, Integer> {
}
