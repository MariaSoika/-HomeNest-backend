package org.example.coursework.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.coursework.dto.ApartmentCreateDto;
import org.example.coursework.dto.ApartmentDto;
import org.example.coursework.exception.ApartmentNotFoundException;
import org.example.coursework.service.ApartmentService;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

@RestController
@RequestMapping("/api/apartments")
@RequiredArgsConstructor
public class ApartmentController {

    private final ApartmentService apartmentService;

    @PostMapping
    public ResponseEntity<ApartmentDto> createApartment(@Valid @RequestBody ApartmentCreateDto apartmentCreateDto) {
        ApartmentDto createdApartment = apartmentService.create(apartmentCreateDto);
        return new ResponseEntity<>(createdApartment, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteApartment(@PathVariable Long id) throws ApartmentNotFoundException {
        apartmentService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApartmentDto> updateApartment(
            @PathVariable Long id,
            @Valid @RequestBody ApartmentDto apartmentDto) throws ApartmentNotFoundException {
        ApartmentDto updatedApartment = apartmentService.update(id, apartmentDto);
        return ResponseEntity.ok(updatedApartment);
    }

    @GetMapping
    public ResponseEntity<Page<ApartmentDto>> getAllApartments(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<ApartmentDto> apartments = apartmentService.getAll(page, size);
        return ResponseEntity.ok(apartments);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApartmentDto> getApartmentById(@PathVariable Long id) throws ApartmentNotFoundException {
        ApartmentDto apartment = apartmentService. getById(id);
        return ResponseEntity.ok(apartment);
    }
}