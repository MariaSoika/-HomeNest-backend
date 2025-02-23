package org.example.coursework.mapper;

import org.example.coursework.dto.OrderCreateDto;
import org.example.coursework.dto.OrderDto;
import org.example.coursework.entity.Order;
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
