package org.example.coursework.dto;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * DTO for {@link org.example.coursework.entity.Appointment}
 */
public record AppointmentDto(long ID, long userID, long apartmentID,
                             LocalDate appointmentDate) implements Serializable {
}