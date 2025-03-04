package org.homeNest.repository;

import org.homeNest.entity.Apartment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApartmentRepository extends JpaRepository <Apartment, Long> {
}

