package org.homeNest.mapper;

import org.homeNest.dto.OrderCreateDto;
import org.homeNest.dto.OrderReportDto;
import org.homeNest.entity.OrderReport;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING, uses = {OrderMapper.class})
public interface OrderReportMapper {
    @Mapping(source = "orderID", target = "order.id")
    OrderReport toEntity(OrderReportDto orderReportDto);

    @Mapping(source = "order.id", target = "orderID")
    OrderReportDto toDto(OrderReport orderReport);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    OrderReport partialUpdate(OrderReportDto orderReportDto, @MappingTarget OrderReport orderReport);

    OrderReport toEntity(OrderCreateDto orderCreateDto);
}