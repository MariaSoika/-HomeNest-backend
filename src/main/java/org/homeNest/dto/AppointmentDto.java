package org.homeNest.dto;

import org.homeNest.entity.Appointment;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * DTO for {@link Appointment}
 */
public record AppointmentDto(long id, long userID, long apartmentID,
                             LocalDate appointmentDate,
                             String description) implements Serializable {
}