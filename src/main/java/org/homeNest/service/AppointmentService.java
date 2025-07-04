package org.homeNest.service;

import lombok.AllArgsConstructor;
import org.homeNest.dto.AppointmentCreateDto;
import org.homeNest.dto.AppointmentDto;
import org.homeNest.entity.Appointment;
import org.homeNest.entity.AppointmentReport;
import org.homeNest.exception.AppointmentNotFoundException;
import org.homeNest.mapper.AppointmentMapper;
import org.homeNest.mapper.AppointmentReportMapper;
import org.homeNest.repository.ApartmentRepository;
import org.homeNest.repository.AppointmentReportRepository;
import org.homeNest.repository.AppointmentRepository;
import org.homeNest.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@AllArgsConstructor
public class AppointmentService {

    private static final Logger logger = LoggerFactory.getLogger(AppointmentService.class);

    private final AppointmentRepository appointmentRepository;
    private final AppointmentMapper appointmentMapper;
    private final AppointmentReportRepository appointmentReportRepository;
    private final AppointmentReportMapper appointmentReportMapper;
    private final UserRepository userRepository;
    private final ApartmentRepository apartmentRepository;

    @CacheEvict(value = "appointments", allEntries = true)
    @Transactional
    public AppointmentDto create(AppointmentCreateDto appointmentCreateDto) {
        Appointment appointment = appointmentMapper.toEntity(appointmentCreateDto);
        appointment.setUser(userRepository.getReferenceById(appointmentCreateDto.userID()));
        appointment.setApartment(apartmentRepository.getReferenceById(appointmentCreateDto.apartmentID()));
        appointment.setDescription(appointmentCreateDto.description());
        appointment.setAppointmentDate(appointmentCreateDto.appointmentDate());
        appointment = appointmentRepository.save(appointment);

//        AppointmentReport appointmentReport = appointmentReportMapper.toEntity(appointmentCreateDto);
//        appointmentReport.setAppointment(appointment);
//        appointmentReport.setDescription(appointmentCreateDto.description());
//        appointmentReportRepository.save(appointmentReport);

        logger.info("Created appointment with ID: {}", appointment.getId());
//        logger.info("Created apartmentReport with ID: {}", appointmentReport.getId());
        return appointmentMapper.toDto(appointment);
    }

    @CacheEvict(value = "appointments", key = "#appointmentID")
    @Transactional
    public void delete(Long appointmentID) throws AppointmentNotFoundException {
        if (appointmentRepository.existsById(appointmentID)) {
            appointmentRepository.deleteById(appointmentID);
            logger.info("Deleted appointment with ID: {}", appointmentID);
        } else {
            logger.error("Appointment with ID{}does not exist", appointmentID);
            throw new AppointmentNotFoundException("Appointment with ID " + appointmentID + "does nor exist");
        }
    }

    @CacheEvict(value = "appointments", key = "#appointmentID")
    @Transactional
    public AppointmentDto update(Long appointmentID, AppointmentDto appointmentDto) throws AppointmentNotFoundException {
        return appointmentRepository.findById(appointmentID)
                .map(existingAppointment -> {
                    if (appointmentRepository.existsById(appointmentID)) {
                        existingAppointment.setUser(userRepository.getReferenceById(appointmentDto.userID()));
                        existingAppointment.setApartment(apartmentRepository.getReferenceById(appointmentDto.apartmentID()));
                    }
                    existingAppointment.setAppointmentDate(appointmentDto.appointmentDate());
                    logger.info("Updated appointment with ID: {}", existingAppointment.getId());
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
