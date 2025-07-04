package org.homeNest.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.homeNest.entity.Apartment;
import org.homeNest.enums.*;

import java.io.Serializable;
import java.util.List;

/**
 * DTO for {@link Apartment}
 */
public record ApartmentDto(long id, Long residentialComplexId,
                           String residentialComplexName,
                           SellingStatus sellingStatus,
                           PropertyAvailabilityStatus propertyAvailabilityStatus,
                           PropertyVerificationStatus propertyVerificationStatus,
                           String photos,
                           @NotBlank(message = "Title is mandatory") String title,
                           @Positive(message = "Price must be greater than 0") double price,
                           @Positive(message = "Area must be greater than 0") double area,
                           @Min(message = "Rooms must be at least 1", value = 1) int rooms,
                           @Min(message = "Floor must be at least 0", value = 0) int floor,
                           @NotBlank(message = "Address is mandatory") String address, HeatingType heatingType,
                           WaterSupplyType waterSupplyType, StateOfRepair stateOfRepair,
                           String description) implements Serializable {
}