package org.example.coursework.dto;

import jakarta.validation.constraints.NotNull;
import org.example.coursework.entity.Apartment;

import java.io.Serializable;

/**
 * DTO for {@link org.example.coursework.entity.Apartment}
 */
public record ApartmentDto(String photo, String title, double price, double area, int rooms, int floor, String address,
                            Apartment.Status status) implements Serializable {
}