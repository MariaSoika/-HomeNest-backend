package org.example.coursework.service;

import lombok.AllArgsConstructor;
import org.example.coursework.dto.RentCreateDto;
import org.example.coursework.dto.RentDto;
import org.example.coursework.entity.Rent;
import org.example.coursework.mapper.RentMapper;
import org.example.coursework.repository.RentRepository;
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
public class RentService {

    private static final Logger logger = LoggerFactory.getLogger(RentService.class);

    private final RentRepository rentRepository;
    private final RentMapper rentMapper;

    @CacheEvict(value = "rents", allEntries = true)
    @Transactional
    public RentDto create(RentCreateDto rentCreateDto) {
        Rent rent = rentMapper.toEntity(rentCreateDto);
        logger.info("Create new rent contract: {}", rent);
        return rentMapper.toDto(rentRepository.save(rent));
    }

    @CacheEvict(value = "rents", key = "#rentId")
    @Transactional
    public void delete(Long rentId) {
        if (rentRepository.existsById(rentId)) {
            rentRepository.deleteById(rentId);
            logger.info("Delete rent contract: {}", rentId);
        }else {
            logger.error("Rent with id {} does not exist", rentId);
        }
    }

    @CacheEvict(value = "rents", key = "#rentId")
    @Transactional
    public RentDto update(Long rentId, RentDto rentDto) {
        logger.info("Update rent contract: {}", rentDto);
        return rentRepository.findById(rentId)
                .map(rent -> {
                    Rent updatedRent = rentMapper.toEntity(rentDto);
                    updatedRent.setId(rentId);
                    return rentMapper.toDto(rentRepository.save(updatedRent));
                })
                .orElseThrow(() -> {
                    logger.error("Rent with id {} does not exist", rentId);
                    return new RuntimeException("Rent with id " + rentId + " does not exist");
                });
    }

    @Cacheable(value = "rents", key = "#page + '-' + #size")
    @Transactional
    public Page<RentDto> getAll(int page, int size) {
        logger.info("Get all rents contract");
        Pageable pageable = Pageable.ofSize(size).withPage(page);
        return rentRepository.findAll(pageable)
                .map(rentMapper::toDto);
    }

    @Cacheable(value = "rents", key = "#rentId")
    @Transactional
    public RentDto getById(Long rentId) {
        logger.info("Get rent contract: {}", rentId);
        return rentRepository.findById(rentId)
                .map(rentMapper::toDto)
                .orElse(null);
    }
}
