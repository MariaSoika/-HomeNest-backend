package org.homeNest.repository;

import org.homeNest.dto.ApartmentDto;
import org.homeNest.entity.Apartment;
import org.homeNest.enums.SellingStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApartmentRepository extends JpaRepository <Apartment, Long> {
    List<Apartment> findBySellingStatus(SellingStatus status);

}

