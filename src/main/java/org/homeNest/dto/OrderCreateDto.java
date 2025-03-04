package org.homeNest.dto;

import org.homeNest.entity.Order;

import java.io.Serializable;

/**
 * DTO for {@link Order}
 */

public record OrderCreateDto(long userID, long apartmentID ,
                             String description) implements Serializable{
}
////
