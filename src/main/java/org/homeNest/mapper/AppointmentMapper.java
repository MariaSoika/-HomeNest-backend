package org.homeNest.mapper;

import org.homeNest.dto.AppointmentCreateDto;
import org.homeNest.dto.AppointmentDto;
import org.homeNest.entity.Appointment;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface AppointmentMapper {
    @Mapping(source = "apartmentID", target = "apartment.id")
    @Mapping(source = "userID", target = "user.id")
    Appointment toEntity(AppointmentDto appointmentDto);

    @Mapping(source = "apartment.id", target = "apartmentID")
    @Mapping(source = "user.id", target = "userID")
    AppointmentDto toDto(Appointment appointment);

    @InheritConfiguration(name = "toEntity")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Appointment partialUpdate(AppointmentDto appointmentDto, @MappingTarget Appointment appointment);

    Appointment toEntity(AppointmentCreateDto appointmentCreateDto);
}