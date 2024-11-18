package org.example.coursework.dto;

import org.example.coursework.entity.AppointmentReport;

import java.io.Serializable;

/**
 * DTO for {@link AppointmentReport}
 */
public record AppointmentReportDto(Long id, AppointmentDto appointment, String description) implements Serializable {
}