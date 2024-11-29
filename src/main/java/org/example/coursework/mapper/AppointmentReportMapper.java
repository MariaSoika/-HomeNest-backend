package org.example.coursework.mapper;

import org.example.coursework.dto.AppointmentReportCreateDto;
import org.example.coursework.entity.AppointmentReport;
import org.example.coursework.dto.AppointmentReportDto;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING, uses = {AppointmentMapper.class})
public interface AppointmentReportMapper {
    @Mapping(source = "appointmentID", target = "appointment.ID")
    AppointmentReport toEntity(AppointmentReportDto appointmentReportDto);

    @Mapping(source = "appointment.ID", target = "appointmentID")
    AppointmentReportDto toDto(AppointmentReport appointmentReport);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    AppointmentReport partialUpdate(AppointmentReportDto appointmentReportDto, @MappingTarget AppointmentReport appointmentReport);

    AppointmentReport toEntity(AppointmentReportCreateDto appointmentReportCreateDto);
}