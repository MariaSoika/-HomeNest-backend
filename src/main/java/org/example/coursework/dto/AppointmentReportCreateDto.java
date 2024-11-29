package org.example.coursework.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;

/**
 * DTO for {@link org.example.coursework.entity.AppointmentReport}
 */
public record AppointmentReportCreateDto(
        @NotNull(message = "AppointmentID is mandatory")
        long appointmentID,
        @NotBlank(message = "Description is mandatory")
        String description) implements Serializable {
}