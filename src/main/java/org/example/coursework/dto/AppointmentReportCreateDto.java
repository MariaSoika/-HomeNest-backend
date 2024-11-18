package org.example.coursework.dto;

import java.io.Serializable;

/**
 * DTO for {@link org.example.coursework.entity.AppointmentReport}
 */
public record AppointmentReportCreateDto(AppointmentDto appointment,
                                        String description) implements Serializable {
}