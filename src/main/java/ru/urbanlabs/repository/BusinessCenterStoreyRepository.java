package ru.urbanlabs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.urbanlabs.model.businesscenter.BusinessCenterStorey;

public interface BusinessCenterStoreyRepository extends JpaRepository<BusinessCenterStorey, Integer> {
}
