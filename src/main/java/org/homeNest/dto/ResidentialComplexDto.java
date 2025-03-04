package org.homeNest.dto;

import org.homeNest.entity.ResidentialComplex;
import org.homeNest.enums.BuildingConstructionType;

import java.io.Serializable;
import java.util.List;

/**
 * DTO for {@link ResidentialComplex}
 */
public record ResidentialComplexDto(Long id, String name, String address,
                                    BuildingConstructionType buildingConstructionType, String description,
                                    List<String> photos, List<ApartmentDto> apartments) implements Serializable {
}