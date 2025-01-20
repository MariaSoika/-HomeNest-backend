package org.example.coursework.dto;

import org.example.coursework.enums.RentStatus;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * DTO for {@link org.example.coursework.entity.Rent}
 */
public record RentDto(long id, ApartmentDto apartment, UserDto user, LocalDate startDate, LocalDate endDate,
                      Double price, Double deposit, RentStatus status, String description) implements Serializable {
}