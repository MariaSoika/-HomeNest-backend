package org.homeNest.mapper;

import org.homeNest.dto.HouseCreateDto;
import org.homeNest.dto.HouseDto;
import org.homeNest.entity.House;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface HouseMapper {
    House toEntity(HouseDto houseDto);

    HouseDto toDto(House house);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    House partialUpdate(HouseDto houseDto, @MappingTarget House house);

    House toEntity(HouseCreateDto houseCreateDto);
}