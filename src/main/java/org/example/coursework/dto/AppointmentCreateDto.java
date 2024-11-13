package org.example.coursework.dto;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * DTO for {@link org.example.coursework.entity.Appointment}
 */
public record AppointmentCreateDto(long userID, long apartmentID, String apartmentPhoto, String apartmentTitle,
                                   LocalDate appointmentDate) implements Serializable {
}