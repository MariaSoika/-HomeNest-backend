package org.homeNest.dto;

import jakarta.validation.constraints.NotNull;
import org.homeNest.entity.Appointment;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * DTO for {@link Appointment}
 */
public record AppointmentCreateDto(
        @NotNull(message = "UserID is mandatory")
        long userID,
        @NotNull(message = "apartmentID is mandatory")
        long apartmentID,
        @NotNull(message = "Appointment date is mandatory")
        LocalDate appointmentDate,
        String description) implements Serializable {
}