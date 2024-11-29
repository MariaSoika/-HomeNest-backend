package org.example.coursework.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.coursework.dto.AppointmentCreateDto;
import org.example.coursework.dto.AppointmentDto;
import org.example.coursework.exception.AppointmentNotFoundException;
import org.example.coursework.service.AppointmentService;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

@RestController
@RequestMapping("/api/appointments")
@RequiredArgsConstructor
public class AppointmentController {

    private final AppointmentService appointmentService;

    @PostMapping
    public ResponseEntity<AppointmentDto> createAppointment(@Valid @RequestBody AppointmentCreateDto appointmentCreateDto) {
        AppointmentDto createdAppointment = appointmentService.create(appointmentCreateDto);
        return new ResponseEntity<>(createdAppointment, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAppointment(@PathVariable Long id) throws AppointmentNotFoundException {
        appointmentService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AppointmentDto> updateAppointment(
            @PathVariable Long id,
            @Valid @RequestBody AppointmentDto appointmentDto) throws AppointmentNotFoundException {
        AppointmentDto updatedAppointment = appointmentService.update(id, appointmentDto);
        return ResponseEntity.ok(updatedAppointment);
    }

    @GetMapping
    public ResponseEntity<Page<AppointmentDto>> getAllAppointments(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<AppointmentDto> appointments = appointmentService.getAll(page, size);
        return ResponseEntity.ok(appointments);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AppointmentDto> getAppointmentById(@PathVariable Long id) throws AppointmentNotFoundException {
        AppointmentDto appointment = appointmentService.getById(id);
        return ResponseEntity.ok(appointment);
    }
}