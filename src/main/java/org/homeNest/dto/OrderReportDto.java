package org.homeNest.dto;

import org.homeNest.entity.OrderReport;

import java.io.Serializable;

/**
 * DTO for {@link OrderReport}
 */
public record OrderReportDto(Long id, long orderID, String description) implements Serializable {
}