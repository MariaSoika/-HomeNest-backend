package org.example.coursework.service;

import lombok.AllArgsConstructor;
import org.example.coursework.dto.AppointmentCreateDto;
import org.example.coursework.dto.AppointmentDto;
import org.example.coursework.entity.Appointment;
import org.example.coursework.mapper.AppointmentMapper;
import org.example.coursework.repository.ApartmentRepository;
import org.example.coursework.repository.AppointmentRepository;
import org.example.coursework.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@AllArgsConstructor
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final AppointmentMapper appointmentMapper;
    private final UserRepository userRepository;
    private final ApartmentRepository apartmentRepository;

    @Transactional
    public AppointmentDto create(AppointmentCreateDto appointmentCreateDto) {
        Appointment appointment = appointmentMapper.toEntity(appointmentCreateDto);

        return appointmentMapper.toDto(appointmentRepository.save(appointment));
    }

    @Transactional
    public void delete(Long appointmentID) {
        if (appointmentRepository.existsById(appointmentID)) {
            appointmentRepository.deleteById(appointmentID);
        } else {
            throw new IllegalArgumentException("Appointment with ID " + appointmentID + "does nor exist");
        }
    }

    @Transactional
    public AppointmentDto update(Long appointmentID, AppointmentDto appointmentDto){
        return appointmentRepository.findById(appointmentID)
                //check if id equals existing id
                .map(existingAppointment -> {
                    existingAppointment.setUser(userRepository.getReferenceById(appointmentDto.userID()));
                    existingAppointment.setApartment(apartmentRepository.getReferenceById(appointmentDto.apartmentID()));
                    existingAppointment.setAppointmentDate(LocalDate.now());

                    return appointmentMapper.toDto(appointmentRepository.save(existingAppointment));
                })
                .orElseThrow(() -> new IllegalArgumentException("Appointment with ID " + appointmentID + " does not exist"));

    }

    @Transactional
    public Page<AppointmentDto> getAll(int page, int size) {
        Pageable pageable = Pageable.ofSize(size).withPage(page);
        return appointmentRepository.findAll(pageable)
                .map(appointmentMapper::toDto);
    }

    @Transactional
    public AppointmentDto getById(Long appointmentID) {
        return appointmentRepository.findById(appointmentID)
                .map(appointmentMapper::toDto)
                .orElseThrow(() -> new IllegalArgumentException("Appointment with ID " + appointmentID + " does not exist"));
    }
}
