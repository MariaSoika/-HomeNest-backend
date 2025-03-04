package org.homeNest.service;

import lombok.AllArgsConstructor;
import org.homeNest.dto.ApartmentCreateDto;
import org.homeNest.dto.ApartmentDto;
import org.homeNest.entity.Apartment;
import org.homeNest.exception.ApartmentNotFoundException;
import org.homeNest.mapper.ApartmentMapper;
import org.homeNest.repository.ApartmentRepository;
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
public class ApartmentService {

    private static final Logger logger = LoggerFactory.getLogger(ApartmentService.class);

    private final ApartmentMapper apartmentMapper;
    private final ApartmentRepository apartmentRepository;

    @CacheEvict(value = "apartments", allEntries = true)
    @Transactional
    public ApartmentDto create(ApartmentCreateDto apartmentCreateDto) {
        logger.info("Creating apartment...");
        Apartment apartment = apartmentMapper.toEntity(apartmentCreateDto);
        logger.info("Created apartment with ID: {}", apartment.getId());
        return apartmentMapper.toDto(apartmentRepository.save(apartment));
    }

    @CacheEvict(value = "apartments", key = "#apartmentId")
    @Transactional
    public void delete(Long apartmentId) throws ApartmentNotFoundException {
        logger.info("Deleting apartment with ID: {}", apartmentId);
        if (apartmentRepository.existsById(apartmentId)) {
            apartmentRepository.deleteById(apartmentId);
            logger.info("Deleted apartment with ID: {}", apartmentId); 
        } else {
            logger.error("Apartment with ID {} does not exist", apartmentId);
            throw new ApartmentNotFoundException("Apartment with ID " + apartmentId + " does not exist");
        }
    }

    @CacheEvict(value = "apartments", key = "#apartmentId")
    @Transactional
    public ApartmentDto update(Long apartmentId, ApartmentDto apartmentDto) throws ApartmentNotFoundException {
        logger.info("Updating apartment with ID: {}", apartmentId);
        return apartmentRepository.findById(apartmentId)
                .map(existingApartment -> {
                    Apartment updatedApartment = apartmentMapper.toEntity(apartmentDto);
                    updatedApartment.setId(apartmentId);
                    logger.info("Updated apartment with ID: {}", apartmentId);
                    return apartmentMapper.toDto(apartmentRepository.save(updatedApartment));
                })
                .orElseThrow(() -> {
                    logger.error("Apartment with ID: {} does not exist", apartmentId);
                    return new ApartmentNotFoundException("Apartment with ID " + apartmentId + " does not exist");
                });
    }

    @Cacheable(value = "apartments", key = "#page + '-' + #size")
    @Transactional
    public Page<ApartmentDto> getAll(int page, int size) {
        logger.info("Fetching all apartments - Page: {}, Size: {}", page, size);
        Pageable pageable = Pageable.ofSize(size).withPage(page);
        return apartmentRepository.findAll(pageable)
                .map(apartmentMapper::toDto);
    }

    @Cacheable(value = "apartments", key = "#apartmentId")
    @Transactional
    public ApartmentDto getById(Long apartmentId) throws ApartmentNotFoundException {
        logger.info("Fetching apartment with ID: {}", apartmentId);
        return apartmentRepository.findById(apartmentId)
                .map(apartmentMapper::toDto)
                .orElseThrow(() -> {
                    logger.error("Apartment with ID: {} does not exist", apartmentId);
                    return new ApartmentNotFoundException("Apartment with ID " + apartmentId + " does not exist");
                });
    }
}


