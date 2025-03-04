package org.homeNest.mapper;

import org.homeNest.dto.AppointmentCreateDto;
import org.homeNest.entity.AppointmentReport;
import org.homeNest.dto.AppointmentReportDto;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING, uses = {AppointmentMapper.class})
public interface AppointmentReportMapper {
    @Mapping(source = "appointmentID", target = "appointment.id")
    AppointmentReport toEntity(AppointmentReportDto appointmentReportDto);

    @Mapping(source = "appointment.id", target = "appointmentID")
    AppointmentReportDto toDto(AppointmentReport appointmentReport);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    AppointmentReport partialUpdate(AppointmentReportDto appointmentReportDto, @MappingTarget AppointmentReport appointmentReport);
    AppointmentReport toEntity(AppointmentCreateDto appointmentCreateDto);
}