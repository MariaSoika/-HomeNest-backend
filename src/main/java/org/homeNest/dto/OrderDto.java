package org.homeNest.dto;

import org.homeNest.entity.Order;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * DTO for {@link Order}
 */

public record OrderDto (long id, long userID, long apartmentID,
                        LocalDate orderDate,
                        String description) implements Serializable {
}