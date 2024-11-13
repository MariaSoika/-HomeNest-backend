package org.example.coursework.mapper;

import org.example.coursework.dto.AppointmentCreateDto;
import org.example.coursework.dto.AppointmentDto;
import org.example.coursework.entity.Appointment;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface AppointmentMapper {
    @Mapping(source = "apartmentTitle", target = "apartment.title")
    @Mapping(source = "apartmentPhoto", target = "apartment.photo")
    @Mapping(source = "apartmentID", target = "apartment.ID")
    @Mapping(source = "userID", target = "user.ID")
    Appointment toEntity(AppointmentDto appointmentDto);

    @InheritInverseConfiguration(name = "toEntity")
    AppointmentDto toDto(Appointment appointment);

    @InheritConfiguration(name = "toEntity")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Appointment partialUpdate(AppointmentDto appointmentDto, @MappingTarget Appointment appointment);

    Appointment toEntity(AppointmentCreateDto appointmentCreateDto);
}