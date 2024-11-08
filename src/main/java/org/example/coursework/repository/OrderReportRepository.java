package org.example.coursework.repository;

import org.example.coursework.entity.OrderReport;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderReportRepository extends JpaRepository<OrderReport, Long> {
}
