package org.homeNest.repository;

import org.homeNest.entity.ResidentialComplex;
import org.homeNest.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ResidentialComplexRepository extends JpaRepository<ResidentialComplex, Long> {
    Optional<ResidentialComplex> findByName(String name);
}