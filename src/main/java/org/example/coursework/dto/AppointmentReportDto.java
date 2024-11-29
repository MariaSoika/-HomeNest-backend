package org.example.coursework.dto;

import org.example.coursework.entity.AppointmentReport;

import java.io.Serializable;

/**
 * DTO for {@link AppointmentReport}
 */
public record AppointmentReportDto(Long id, long appointmentID, String description) implements Serializable {
}