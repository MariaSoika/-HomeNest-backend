package org.example.coursework.service;

import lombok.AllArgsConstructor;
import org.example.coursework.dto.AppointmentReportCreateDto;
import org.example.coursework.dto.AppointmentReportDto;
import org.example.coursework.entity.AppointmentReport;
import org.example.coursework.mapper.AppointmentReportMapper;
import org.example.coursework.repository.AppointmentReportRepository;
import org.example.coursework.repository.AppointmentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@AllArgsConstructor
public class AppointmentReportService {

    private final AppointmentReportMapper appointmentReportMapper;
    private final AppointmentReportRepository appointmentReportRepository;
    private AppointmentRepository appointmentRepository;

    @Transactional
    public AppointmentReportDto create(AppointmentReportCreateDto appointmentReportCreateDto) {
        AppointmentReport appointmentReport = new AppointmentReport();
        appointmentReport.setAppointment(appointmentRepository.getReferenceById(appointmentReportCreateDto.appointment().ID()));
        appointmentReport.setDescription(appointmentReportCreateDto.description());

        return appointmentReportMapper.toDto(appointmentReportRepository.save(appointmentReport));
    }

    @Transactional
    public void delete(Long appointmentReportId) {
        if (appointmentReportRepository.existsById(appointmentReportId)) {
            appointmentReportRepository.deleteById(appointmentReportId);
        } else {
            throw new IllegalArgumentException("Appointment report with ID" + appointmentReportId + "does not exist");
        }
    }

    @Transactional
    public AppointmentReportDto update(Long appointmentReportId, AppointmentReportDto appointmentReportDto) {
        return appointmentReportRepository.findById(appointmentReportId)
                .map(appointmentReport -> {
                    appointmentReport.setAppointment(appointmentRepository.getReferenceById(appointmentReportDto.appointment().ID()));
                    appointmentReport.setDescription(appointmentReportDto.description());
                    return appointmentReportMapper.toDto(appointmentReportRepository.save(appointmentReport));
                })
                .orElseThrow(() -> new IllegalArgumentException("Appointment report with ID" + appointmentReportId + "does not exist"));
    }

    @Transactional
    public List<AppointmentReportDto> getAll() {
        return appointmentReportRepository.findAll().stream()
                .map(appointmentReportMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public AppointmentReportDto getById(Long appointmentReportId) {
        return appointmentReportRepository.findById(appointmentReportId)
                .map(appointmentReportMapper::toDto)
                .orElseThrow(() -> new IllegalArgumentException("Appointment report with ID" + appointmentReportId + "does not exist"));
    }
}
