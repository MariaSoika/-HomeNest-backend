package org.example.coursework.mapper;

import org.example.coursework.dto.OrderReportCreateDto;
import org.example.coursework.dto.OrderReportDto;
import org.example.coursework.entity.OrderReport;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING, uses = {OrderMapper.class})
public interface OrderReportMapper {
    OrderReport toEntity(OrderReportDto orderReportDto);

    OrderReportDto toDto(OrderReport orderReport);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    OrderReport partialUpdate(OrderReportDto orderReportDto, @MappingTarget OrderReport orderReport);

    OrderReport toEntity(OrderReportCreateDto orderReportCreateDto);
}