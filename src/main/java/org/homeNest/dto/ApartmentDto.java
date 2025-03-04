package org.homeNest.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.homeNest.entity.Apartment;
import org.homeNest.enums.HeatingType;
import org.homeNest.enums.StateOfRepair;
import org.homeNest.enums.WaterSupplyType;

import java.io.Serializable;

/**
 * DTO for {@link Apartment}
 */
public record ApartmentDto(long id, long residentialComplexId, @NotBlank(message = "Photo is mandatory") String photo,
                           @NotBlank(message = "Title is mandatory") String title,
                           @Positive(message = "Price must be greater than 0") double price,
                           @Positive(message = "Area must be greater than 0") double area,
                           @Min(message = "Rooms must be at least 1", value = 1) int rooms,
                           @Min(message = "Floor must be at least 0", value = 0) int floor,
                           @NotBlank(message = "Address is mandatory") String address,
                           @NotNull(message = "Status is mandatory") Apartment.Status status, HeatingType heatingType,
                           WaterSupplyType waterSupplyType, StateOfRepair stateOfRepair) implements Serializable {
}