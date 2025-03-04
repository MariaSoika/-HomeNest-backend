package org.homeNest.mapper;

import org.homeNest.dto.RentCreateDto;
import org.homeNest.dto.RentDto;
import org.homeNest.entity.Rent;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING, uses = {ApartmentMapper.class, UserMapper.class})
public interface RentMapper {
    @Mapping(target = "id", source = "id")
    @Mapping(target = "apartment", source = "apartment")
    @Mapping(target = "user", source = "user")
    Rent toEntity(RentDto rentDto);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "apartment", source = "apartment")
    @Mapping(target = "user", source = "user")
    RentDto toDto(Rent rent);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Rent partialUpdate(RentDto rentDto, @MappingTarget Rent rent);

    @Mapping(target = "apartment", source = "apartment")
    @Mapping(target = "user", source = "user")
    Rent toEntity(RentCreateDto rentCreateDto);
}