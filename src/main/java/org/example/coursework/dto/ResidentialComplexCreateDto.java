package org.example.coursework.dto;

import org.example.coursework.entity.ResidentialComplex;
import org.example.coursework.enums.BuildingConstructionType;

import java.io.Serializable;
import java.util.List;

/**
 * DTO for {@link ResidentialComplex}
 */
public record ResidentialComplexCreateDto(String name, String address,
                                          BuildingConstructionType buildingConstructionType, String description,
                                          List<String> photos, List<Long> apartmentIDS) implements Serializable {
}