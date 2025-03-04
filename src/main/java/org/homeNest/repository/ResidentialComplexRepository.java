package org.homeNest.repository;

import org.homeNest.entity.ResidentialComplex;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResidentialComplexRepository extends JpaRepository<ResidentialComplex, Long> {
}