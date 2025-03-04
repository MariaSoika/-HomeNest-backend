package org.homeNest.repository;

import org.homeNest.entity.OrderReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderReportRepository extends JpaRepository<OrderReport, Long> {
}
