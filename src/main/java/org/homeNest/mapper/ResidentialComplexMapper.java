package org.homeNest.mapper;

import org.homeNest.dto.ResidentialComplexCreateDto;
import org.homeNest.entity.ResidentialComplex;
import org.homeNest.dto.ResidentialComplexDto;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface ResidentialComplexMapper {
    @Mapping(target = "id", source = "id")
    @Mapping(target = "apartments", source = "apartments")
    ResidentialComplex toEntity(ResidentialComplexDto residentialComplexDto);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "apartments", source = "apartments")
    ResidentialComplexDto toDto(ResidentialComplex residentialComplex);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    ResidentialComplex partialUpdate(ResidentialComplexDto residentialComplexDto, @MappingTarget ResidentialComplex residentialComplex);

    ResidentialComplex toEntity(ResidentialComplexCreateDto residentialComplexCreateDto);

}