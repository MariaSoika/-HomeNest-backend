package org.homeNest.repository;

import org.homeNest.entity.AppointmentReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppointmentReportRepository extends JpaRepository<AppointmentReport, Long> {
}
