package org.homeNest.dto;

import org.homeNest.enums.RentStatus;
import org.homeNest.entity.Rent;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * DTO for {@link Rent}
 */
public record RentCreateDto(ApartmentDto apartment, UserDto user, LocalDate startDate, LocalDate endDate, Double price,
                            Double deposit, RentStatus status, String description) implements Serializable {
}