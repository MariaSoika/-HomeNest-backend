package org.example.coursework.mapper;

import org.example.coursework.dto.ApartmentCreateDto;
import org.example.coursework.dto.ApartmentDto;
import org.example.coursework.entity.Apartment;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface ApartmentMapper {
    @Mapping(source = "residentialComplexId", target = "residentialComplex.Id")
    Apartment toEntity(ApartmentDto apartmentDto);

    @Mapping(source = "residentialComplex.Id", target = "residentialComplexId")
    ApartmentDto toDto(Apartment apartment);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(source = "residentialComplexId", target = "residentialComplex.Id")
    Apartment partialUpdate(ApartmentDto apartmentDto, @MappingTarget Apartment apartment);

    Apartment toEntity(ApartmentCreateDto apartmentCreateDto);

}