package org.homeNest.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.homeNest.dto.AppointmentReportDto;
import org.homeNest.exception.AppointmentReportNotFoundException;
import org.homeNest.service.AppointmentReportService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;

@RestController
@RequestMapping("/api/appointment-reports")
@RequiredArgsConstructor
public class AppointmentReportController {

    private final AppointmentReportService appointmentReportService;

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAppointmentReport(@PathVariable Long id) throws AppointmentReportNotFoundException {
        appointmentReportService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AppointmentReportDto> updateAppointmentReport(
            @PathVariable Long id,
            @Valid @RequestBody AppointmentReportDto appointmentReportDto) throws AppointmentReportNotFoundException {
        AppointmentReportDto updatedAppointmentReport = appointmentReportService.update(id, appointmentReportDto);
        return ResponseEntity.ok(updatedAppointmentReport);
    }

    @GetMapping
    public ResponseEntity<Page<AppointmentReportDto>> getAllAppointmentReports(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Page<AppointmentReportDto> appointmentReports = appointmentReportService.getAll(page, size);
        return ResponseEntity.ok(appointmentReports);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AppointmentReportDto> getAppointmentReportById(@PathVariable Long id) throws AppointmentReportNotFoundException {
        AppointmentReportDto appointmentReport = appointmentReportService.getById(id);
        return ResponseEntity.ok(appointmentReport);
    }
}