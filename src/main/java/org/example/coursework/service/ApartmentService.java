package org.example.coursework.service;


import lombok.AllArgsConstructor;
import org.example.coursework.dto.ApartmentCreateDto;
import org.example.coursework.dto.ApartmentDto;
import org.example.coursework.entity.Apartment;
import org.example.coursework.mapper.ApartmentMapper;
import org.example.coursework.repository.ApartmentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@AllArgsConstructor
public class ApartmentService {

    private final ApartmentMapper apartmentMapper;
    private final ApartmentRepository apartmentRepository;

    @Transactional
    public ApartmentDto create(ApartmentCreateDto apartmentCreateDto) {
        Apartment apartment = apartmentMapper.toEntity(apartmentCreateDto);

        return apartmentMapper.toDto(apartmentRepository.save(apartment));
    }

    @Transactional
    public void delete(Long apartmentId) {
        if (apartmentRepository.existsById(apartmentId)) {
            apartmentRepository.deleteById(apartmentId);
        } else {
            throw new IllegalArgumentException("Apartment with ID " + apartmentId + " does not exist");
        }
    }

    @Transactional
    public ApartmentDto update(Long apartmentId, ApartmentDto apartmentDto) {
        return apartmentRepository.findById(apartmentId)
                .map(existingApartment -> {
                    Apartment updatedApartment = apartmentMapper.toEntity(apartmentDto);
                    updatedApartment.setID(apartmentId);
                    return apartmentMapper.toDto(apartmentRepository.save(updatedApartment));
                })
                .orElseThrow(() -> new IllegalArgumentException("Apartment with ID " + apartmentId + " does not exist"));
    }

    @Transactional
    public List<ApartmentDto> getAll() {
        return apartmentRepository.findAll()
                .stream()
                .map(apartmentMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public ApartmentDto getById(Long apartmentId) {
        return apartmentRepository.findById(apartmentId)
                .map(apartmentMapper::toDto)
                .orElseThrow(() -> new IllegalArgumentException("Apartment with ID " + apartmentId + " does not exist"));
    }
}


