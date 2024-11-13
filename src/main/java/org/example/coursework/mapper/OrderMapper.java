package org.example.coursework.mapper;

import org.example.coursework.dto.OrderCreateDto;
import org.example.coursework.dto.OrderDto;
import org.example.coursework.entity.Order;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface OrderMapper {

    @Mapping(source = "apartmentPrice", target = "apartment.price")
    @Mapping(source = "apartmentTitle", target = "apartment.title")
    @Mapping(source = "apartmentID", target = "apartment.ID")
    @Mapping(source = "userID", target = "user.ID")
    Order toEntity(OrderDto orderDto);

    @InheritInverseConfiguration(name = "toEntity")
    OrderDto toDto(Order order);

    @InheritInverseConfiguration(name = "toDto")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Order partialUpdate(OrderDto orderDto, @MappingTarget Order order);

    Order toEntity(OrderCreateDto orderCreateDto);

}
