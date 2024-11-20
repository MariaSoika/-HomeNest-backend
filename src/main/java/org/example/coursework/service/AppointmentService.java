package org.example.coursework.service;

import lombok.AllArgsConstructor;
import org.example.coursework.dto.AppointmentCreateDto;
import org.example.coursework.dto.AppointmentDto;
import org.example.coursework.entity.Appointment;
import org.example.coursework.exception.AppointmentNotFoundException;
import org.example.coursework.mapper.AppointmentMapper;
import org.example.coursework.repository.ApartmentRepository;
import org.example.coursework.repository.AppointmentRepository;
import org.example.coursework.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@Transactional(readOnly = true)
@AllArgsConstructor
public class AppointmentService {

    private static final Logger logger = LoggerFactory.getLogger(AppointmentService.class);

    private final AppointmentRepository appointmentRepository;
    private final AppointmentMapper appointmentMapper;
    private final UserRepository userRepository;
    private final ApartmentRepository apartmentRepository;

    @CacheEvict(value = "appointments", allEntries = true)
    @Transactional
    public AppointmentDto create(AppointmentCreateDto appointmentCreateDto) {
        Appointment appointment = appointmentMapper.toEntity(appointmentCreateDto);
        logger.info("Created apartmentReport with ID: {}", appointment.getID());
        return appointmentMapper.toDto(appointmentRepository.save(appointment));
    }

    @CacheEvict(value = "appointments", key = "#appointmentID")
    @Transactional
    public void delete(Long appointmentID) throws AppointmentNotFoundException {
        if (appointmentRepository.existsById(appointmentID)) {
            appointmentRepository.deleteById(appointmentID);
            logger.info("Deleted appointment with ID: {}", appointmentID);
        } else {
            logger.error("Appointment with ID" + appointmentID + "does not exist");
            throw new AppointmentNotFoundException("Appointment with ID " + appointmentID + "does nor exist");
        }
    }

    @CacheEvict(value = "appointments", key = "#appointmentID")
    @Transactional
    public AppointmentDto update(Long appointmentID, AppointmentDto appointmentDto) throws AppointmentNotFoundException {
        return appointmentRepository.findById(appointmentID)
                //check if id equals existing id
                .map(existingAppointment -> {
                    existingAppointment.setUser(userRepository.getReferenceById(appointmentDto.userID()));
                    existingAppointment.setApartment(apartmentRepository.getReferenceById(appointmentDto.apartmentID()));
                    existingAppointment.setAppointmentDate(LocalDate.now());
                    logger.info("Updated appointment with ID: {}", existingAppointment.getID());
                    return appointmentMapper.toDto(appointmentRepository.save(existingAppointment));
                })
                .orElseThrow(() -> {
                    logger.error("Appointment with ID: {} does not exist", appointmentID);
                    return new AppointmentNotFoundException("Appointment with ID " + appointmentID + " does not exist");
                });

    }

    @Cacheable(value = "appointments", key = "#page + '-' + #size")
    @Transactional
    public Page<AppointmentDto> getAll(int page, int size) {
        Pageable pageable = Pageable.ofSize(size).withPage(page);
        logger.info("Fetching all appointments - Page: {}, Size: {}", page, size);
        return appointmentRepository.findAll(pageable)
                .map(appointmentMapper::toDto);
    }

    @Cacheable(value = "appointments", key = "#appointmentID")
    @Transactional
    public AppointmentDto getById(Long appointmentID) throws AppointmentNotFoundException {
        logger.info("Fetching appointment with ID: {}", appointmentID);
        return appointmentRepository.findById(appointmentID)
                .map(appointmentMapper::toDto)
                .orElseThrow(() -> {
                    logger.error("Appointment with ID {} does not exist", appointmentID);
                    return new AppointmentNotFoundException("Appointment with ID " + appointmentID + " does not exist");
                });
    }
}
