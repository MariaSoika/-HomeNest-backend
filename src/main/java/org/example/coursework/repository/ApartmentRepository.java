package org.example.coursework.repository;

import org.example.coursework.entity.Apartment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApartmentRepository extends JpaRepository <Apartment, Long> {
}

