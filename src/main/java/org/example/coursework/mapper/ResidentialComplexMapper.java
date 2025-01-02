package org.example.coursework.mapper;

import org.example.coursework.dto.ResidentialComplexCreateDto;
import org.example.coursework.entity.ResidentialComplex;
import org.example.coursework.dto.ResidentialComplexDto;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface ResidentialComplexMapper {
    ResidentialComplex toEntity(ResidentialComplexDto residentialComplexDto);

    //@Mapping(target = "apartmentIDS", expression = "java(apartmentsToApartmentIDS(residentialComplex.getApartments()))")
    ResidentialComplexDto toDto(ResidentialComplex residentialComplex);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    ResidentialComplex partialUpdate(ResidentialComplexDto residentialComplexDto, @MappingTarget ResidentialComplex residentialComplex);

    ResidentialComplex toEntity(ResidentialComplexCreateDto residentialComplexCreateDto);

}