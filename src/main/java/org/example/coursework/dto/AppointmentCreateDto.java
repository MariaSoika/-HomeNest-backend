package org.example.coursework.dto;

import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * DTO for {@link org.example.coursework.entity.Appointment}
 */
public record AppointmentCreateDto(
        @NotNull(message = "UserID is mandatory")
        long userID,
        @NotNull(message = "apartmentID is mandatory")
        long apartmentID,
        @NotNull(message = "Appointment date is mandatory")
        LocalDate appointmentDate) implements Serializable {
}