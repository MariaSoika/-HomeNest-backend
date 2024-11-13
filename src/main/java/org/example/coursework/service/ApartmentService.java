package org.example.coursework.service;


import lombok.AllArgsConstructor;
import org.example.coursework.dto.ApartmentCreateDto;
import org.example.coursework.dto.ApartmentDto;
import org.example.coursework.entity.Apartment;
import org.example.coursework.mapper.ApartmentMapper;
import org.example.coursework.repository.ApartmentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public ApartmentDto update(Long apartmentId, ApartmentCreateDto apartmentCreateDto) {
        return apartmentRepository.findById(apartmentId)
                .map(existingApartment -> {
                    Apartment updatedApartment = apartmentMapper.toEntity(apartmentCreateDto);
                    updatedApartment.setID(apartmentId);
                    return apartmentMapper.toDto(apartmentRepository.save(updatedApartment));
                })
                .orElseThrow(() -> new IllegalArgumentException("Apartment with ID " + apartmentId + " does not exist"));
    }

    //getAll TODO

    //getByID TODO
}


