package org.example.coursework.mapper;

import org.example.coursework.dto.AppointmentCreateDto;
import org.example.coursework.dto.AppointmentDto;
import org.example.coursework.entity.Appointment;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface AppointmentMapper {
    @Mapping(source = "apartmentID", target = "apartment.ID")
    @Mapping(source = "userID", target = "user.ID")
    Appointment toEntity(AppointmentDto appointmentDto);

    @Mapping(source = "apartment.ID", target = "apartmentID")
    @Mapping(source = "user.ID", target = "userID")
    AppointmentDto toDto(Appointment appointment);

   // @InheritConfiguration(name = "toEntity")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Appointment partialUpdate(AppointmentDto appointmentDto, @MappingTarget Appointment appointment);

    Appointment toEntity(AppointmentCreateDto appointmentCreateDto);
}