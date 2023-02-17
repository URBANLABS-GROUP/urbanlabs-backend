package org.example.repository;

import org.example.model.businesscenter.BusinessCenter;
import org.example.model.businesscenter.BusinessCenterStorey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BusinessCenterStoreyRepository extends JpaRepository<BusinessCenterStorey, Integer> {
}
