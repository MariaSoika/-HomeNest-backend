package org.example.coursework.dto;

import java.io.Serializable;

/**
 * DTO for {@link org.example.coursework.entity.Order}
 */

public record OrderDto (long ID, long userID, long apartmentID, String apartmentTitle, double apartmentPrice ) {
}
