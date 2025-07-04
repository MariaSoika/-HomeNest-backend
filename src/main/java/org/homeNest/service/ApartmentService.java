package org.homeNest.service;

import lombok.AllArgsConstructor;
import org.homeNest.dto.ApartmentCreateDto;
import org.homeNest.dto.ApartmentDto;
import org.homeNest.entity.Apartment;
import org.homeNest.entity.ResidentialComplex;
import org.homeNest.enums.SellingStatus;
import org.homeNest.exception.ApartmentNotFoundException;
import org.homeNest.mapper.ApartmentMapper;
import org.homeNest.repository.ApartmentRepository;
import org.homeNest.repository.ResidentialComplexRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@AllArgsConstructor
public class ApartmentService {

    private static final Logger logger = LoggerFactory.getLogger(ApartmentService.class);

    private final ApartmentMapper apartmentMapper;
    private final ApartmentRepository apartmentRepository;
    private final ResidentialComplexRepository residentialComplexRepository;

    @CacheEvict(value = "apartments", allEntries = true)
    @Transactional
    public ApartmentDto create(ApartmentCreateDto apartmentCreateDto) throws ApartmentNotFoundException {
        logger.info("Creating apartment...");
        ResidentialComplex complex = residentialComplexRepository
                .findById(apartmentCreateDto.residentialComplexId())
                .orElseThrow(() -> {
                    logger.error("Residential Complex with name: {} does not exist", apartmentCreateDto.residentialComplexId());
                    return new ApartmentNotFoundException("Apartment with ID " + apartmentCreateDto.residentialComplexId() + " does not exist");
                });
        Apartment apartment = apartmentMapper.toEntity(apartmentCreateDto);
        apartment.setResidentialComplex(complex);

        Apartment saved = apartmentRepository.save(apartment);
        logger.info("Created apartment with ID: {}", saved.getId());
        return apartmentMapper.toDto(apartmentRepository.save(saved));
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

    public List<ApartmentDto> getApartmentsForRent() {
        return apartmentRepository.findBySellingStatus(SellingStatus.RENT)
                .stream()
                .map(apartmentMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<ApartmentDto> getApartmentsForByu() {
        return apartmentRepository.findBySellingStatus(SellingStatus.BUY)
                .stream()
                .map(apartmentMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<ApartmentDto> findByIds(List<Long> ids) {
        return apartmentRepository.findAllById(ids).stream()
                .map(apartmentMapper::toDto)
                .collect(Collectors.toList());
    }
}


