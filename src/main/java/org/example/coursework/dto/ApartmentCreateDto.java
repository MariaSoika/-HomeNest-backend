package org.example.coursework.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.example.coursework.entity.Apartment;
import org.example.coursework.enums.HeatingType;
import org.example.coursework.enums.StateOfRepair;
import org.example.coursework.enums.WaterSupplyType;

import java.io.Serializable;

/**
 * DTO for {@link org.example.coursework.entity.Apartment}
 */
public record ApartmentCreateDto(Long residentialComplexId,
                                 @NotBlank(message = "Photo is mandatory") String photo,
                                 @NotBlank(message = "Title is mandatory") String title,
                                 @Positive(message = "Price must be greater than 0") double price,
                                 @Positive(message = "Area must be greater than 0") double area,
                                 @Min(message = "Rooms must be at least 1", value = 1) int rooms,
                                 @Min(message = "Floor must be at least 0", value = 0) int floor,
                                 @NotBlank(message = "Address is mandatory") String address,
                                 @NotNull(message = "Status is mandatory") Apartment.Status status,
                                 HeatingType heatingType, WaterSupplyType waterSupplyType,
                                 StateOfRepair stateOfRepair) implements Serializable {
}