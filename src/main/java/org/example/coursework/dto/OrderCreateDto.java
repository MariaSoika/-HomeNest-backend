package org.example.coursework.dto;

import java.io.Serializable;

/**
 * DTO for {@link org.example.coursework.entity.Order}
 */

public record OrderCreateDto(long userID, long apartmentID) implements Serializable{
}
////////
