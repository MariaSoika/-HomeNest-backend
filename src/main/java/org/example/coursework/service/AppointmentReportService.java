package org.example.coursework.service;

import lombok.AllArgsConstructor;
import org.example.coursework.dto.AppointmentReportCreateDto;
import org.example.coursework.dto.AppointmentReportDto;
import org.example.coursework.entity.AppointmentReport;
import org.example.coursework.exception.AppointmentReportNotFoundException;
import org.example.coursework.mapper.AppointmentReportMapper;
import org.example.coursework.repository.AppointmentReportRepository;
import org.example.coursework.repository.AppointmentRepository;
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
public class AppointmentReportService {

    private static final Logger logger = LoggerFactory.getLogger(AppointmentReportService.class);

    private final AppointmentReportMapper appointmentReportMapper;
    private final AppointmentReportRepository appointmentReportRepository;
    private AppointmentRepository appointmentRepository;

    @CacheEvict(value = "appointmentReport", allEntries = true)
    @Transactional
    public AppointmentReportDto create(AppointmentReportCreateDto appointmentReportCreateDto) {
        AppointmentReport appointmentReport = appointmentReportMapper.toEntity(appointmentReportCreateDto);
        appointmentReport.setAppointment(appointmentRepository.getReferenceById(appointmentReportCreateDto.appointmentID()));
        appointmentReport.setDescription(appointmentReportCreateDto.description());
        appointmentReportRepository.save(appointmentReport);
        logger.info("Created apartmentReport with ID: {}", appointmentReport.getId());
        return appointmentReportMapper.toDto(appointmentReport);
    }

    @CacheEvict(value = "appointmentReports", key = "#appointmentReportId")
    @Transactional
    public void delete(Long appointmentReportId) throws AppointmentReportNotFoundException {
        if (appointmentReportRepository.existsById(appointmentReportId)) {
            appointmentReportRepository.deleteById(appointmentReportId);
            logger.info("Deleted appointmentReport with ID: {}", appointmentReportId);
        } else {
            logger.error("Appointment report with ID" + appointmentReportId + "does not exist");
            throw new AppointmentReportNotFoundException("Appointment report with ID" + appointmentReportId + "does not exist");
        }
    }

    @CacheEvict(value = "appointmentReports", key = "#appointmentReportId")
    @Transactional
    public AppointmentReportDto update(Long appointmentReportId, AppointmentReportDto appointmentReportDto) throws AppointmentReportNotFoundException {
        logger.info("Updating appointmentReport with ID: {}", appointmentReportId);
        return appointmentReportRepository.findById(appointmentReportId)
                .map(appointmentReport -> {
                    if (appointmentReport.getAppointment() != null) {
                        appointmentReport.setAppointment(appointmentRepository.getReferenceById(appointmentReportDto.appointmentID()));
                    }
                    appointmentReport.setDescription(appointmentReportDto.description());
                    appointmentReportRepository.save(appointmentReport);
                    logger.info("Updated appointmentReport with ID: {}", appointmentReport.getId());
                    return appointmentReportMapper.toDto(appointmentReport);
                })
                .orElseThrow(() -> {
                    logger.error("AppointmentReport with ID: {} does not exist", appointmentReportId);
                    return new AppointmentReportNotFoundException("AppointmentReport with ID " + appointmentReportId + " does not exist");
                });
    }

    @Cacheable(value = "appointmentReports", key = "#page + '-' + #size")
    @Transactional
    public Page<AppointmentReportDto> getAll(int page, int size) {
        Pageable pageable = Pageable.ofSize(size).withPage(page);
        logger.info("Fetching all appointmentReports - Page: {}, Size: {}", page, size);
        return appointmentReportRepository.findAll(pageable)
                .map(appointmentReportMapper::toDto);
    }

    @Cacheable(value = "appointmentReports", key = "#appointmentReportId")
    @Transactional
    public AppointmentReportDto getById(Long appointmentReportId) throws AppointmentReportNotFoundException {
        logger.info("Fetching appointmentReport with ID: {}", appointmentReportId);
        return appointmentReportRepository.findById(appointmentReportId)
                .map(appointmentReportMapper::toDto)
                .orElseThrow(() -> {
                    logger.error("AppointmentReport with ID {} does not exist", appointmentReportId);
                    return new AppointmentReportNotFoundException("AppointmentReport with ID " + appointmentReportId + " does not exist");
                });
    }
}
