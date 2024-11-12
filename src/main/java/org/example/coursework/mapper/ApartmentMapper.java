package org.example.coursework.mapper;

import org.example.coursework.dto.ApartmentCreateDto;
import org.example.coursework.dto.ApartmentDto;
import org.example.coursework.entity.Apartment;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface ApartmentMapper {
    Apartment toEntity(ApartmentDto apartmentDto);

    ApartmentDto toDto(Apartment apartment);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Apartment partialUpdate(ApartmentDto apartmentDto, @MappingTarget Apartment apartment);

    Apartment toEntity(ApartmentCreateDto apartmentCreateDto);
}