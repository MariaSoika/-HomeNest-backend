package org.example.coursework.repository;

import org.example.coursework.entity.Apartment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApartmentRepository extends JpaRepository <Apartment, Long> {

}
