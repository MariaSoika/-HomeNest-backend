package org.example.coursework.dto;

import jakarta.validation.constraints.NotNull;

import java.io.Serializable;

/**
 * DTO for {@link org.example.coursework.entity.OrderReport}
 */
public record OrderReportCreateDto(
        @NotNull(message = "OrderID is mandatory")
        long orderID,
        @NotNull(message = "DescriptionID is mandatory")
        String description) implements Serializable {
}