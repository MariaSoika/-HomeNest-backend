package org.example.coursework.repository;

import org.example.coursework.entity.OrderReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderReportRepository extends JpaRepository<OrderReport, Long> {
}
