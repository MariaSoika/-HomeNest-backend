package org.homeNest.mapper;

import org.homeNest.dto.ApartmentCreateDto;
import org.homeNest.dto.ApartmentDto;
import org.homeNest.entity.Apartment;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface ApartmentMapper {
    @Mapping(source = "residentialComplexId", target = "residentialComplex.id")
    @Mapping(source = "residentialComplexName", target = "residentialComplex.name")
    Apartment toEntity(ApartmentDto apartmentDto);

    @Mapping(source = "residentialComplex.name", target = "residentialComplexName")
    @Mapping(source = "residentialComplex.id", target = "residentialComplexId")
    ApartmentDto toDto(Apartment apartment);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(source = "residentialComplexName", target = "residentialComplex.name")
    Apartment partialUpdate(ApartmentDto apartmentDto, @MappingTarget Apartment apartment);

    Apartment toEntity(ApartmentCreateDto apartmentCreateDto);

}