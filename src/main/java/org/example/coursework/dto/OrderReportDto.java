package org.example.coursework.dto;

import java.io.Serializable;

/**
 * DTO for {@link org.example.coursework.entity.OrderReport}
 */
public record OrderReportDto(Long id, long orderID, String description) implements Serializable {
}