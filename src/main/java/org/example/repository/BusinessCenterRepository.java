package org.example.repository;

import org.example.model.businesscenter.BusinessCenter;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BusinessCenterRepository extends JpaRepository<BusinessCenter, Integer> {
}
