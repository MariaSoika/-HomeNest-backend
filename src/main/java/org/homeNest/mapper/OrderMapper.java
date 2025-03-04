package org.homeNest.mapper;

import org.homeNest.dto.OrderCreateDto;
import org.homeNest.dto.OrderDto;
import org.homeNest.entity.Order;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface OrderMapper {

    @Mapping(source = "apartmentID", target = "apartment.id")
    @Mapping(source = "userID", target = "user.id")
    Order toEntity(OrderDto orderDto);

    @Mapping(source = "apartment.id", target = "apartmentID")
    @Mapping(source = "user.id", target = "userID")
    OrderDto toDto(Order order);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Order partialUpdate(OrderDto orderDto, @MappingTarget Order order);

    Order toEntity(OrderCreateDto orderCreateDto);

}
