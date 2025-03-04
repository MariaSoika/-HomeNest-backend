package org.homeNest.dto;

import org.homeNest.entity.AppointmentReport;

import java.io.Serializable;

/**
 * DTO for {@link AppointmentReport}
 */
public record AppointmentReportDto(Long id, long appointmentID, String description) implements Serializable {
}