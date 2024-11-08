package org.example.coursework.repository;

import org.example.coursework.entity.AppointmentReport;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppointmentReportRepository extends JpaRepository<AppointmentReport, Long> {
}
