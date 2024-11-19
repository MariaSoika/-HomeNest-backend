package org.example.coursework.service;


import lombok.AllArgsConstructor;
import org.example.coursework.dto.ApartmentCreateDto;
import org.example.coursework.dto.ApartmentDto;
import org.example.coursework.entity.Apartment;
import org.example.coursework.mapper.ApartmentMapper;
import org.example.coursework.repository.ApartmentRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@AllArgsConstructor
public class ApartmentService {

    private final ApartmentMapper apartmentMapper;
    private final ApartmentRepository apartmentRepository;

    @CacheEvict(value = "apartments", allEntries = true)
    @Transactional
    public ApartmentDto create(ApartmentCreateDto apartmentCreateDto) {
        Apartment apartment = apartmentMapper.toEntity(apartmentCreateDto);

        return apartmentMapper.toDto(apartmentRepository.save(apartment));
    }

    @CacheEvict(value = "apartments", key = "#apartmentId")
    @Transactional
    public void delete(Long apartmentId) {
        if (apartmentRepository.existsById(apartmentId)) {
            apartmentRepository.deleteById(apartmentId);
        } else {
            throw new IllegalArgumentException("Apartment with ID " + apartmentId + " does not exist");
        }
    }

    @CacheEvict(value = "apartments", key = "#apartmentId")
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

    @Cacheable(value = "apartments", key = "#page + '-' + #size")
    @Transactional
    public Page<ApartmentDto> getAll(int page, int size) {
        Pageable pageable = Pageable.ofSize(size).withPage(page);
        return apartmentRepository.findAll(pageable)
                .map(apartmentMapper::toDto);
    }

    @Cacheable(value = "apartments", key = "#apartmentId")
    @Transactional
    public ApartmentDto getById(Long apartmentId) {
        return apartmentRepository.findById(apartmentId)
                .map(apartmentMapper::toDto)
                .orElseThrow(() -> new IllegalArgumentException("Apartment with ID " + apartmentId + " does not exist"));
    }
}


