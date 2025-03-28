package org.homeNest.service;

import lombok.AllArgsConstructor;
import org.homeNest.dto.HouseCreateDto;
import org.homeNest.dto.HouseDto;
import org.homeNest.entity.House;
import org.homeNest.exception.HouseNotFoundException;
import org.homeNest.mapper.HouseMapper;
import org.homeNest.repository.HouseRepository;
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
public class HouseService {

    private static final Logger logger = LoggerFactory.getLogger(HouseService.class);

    private final HouseRepository houseRepository;
    private final HouseMapper houseMapper;

    @CacheEvict(value = "houses", allEntries = true)
    @Transactional
    public HouseDto create(HouseCreateDto houseCreateDto) {
        logger.info("Creating house...");
        House house = houseMapper.toEntity(houseCreateDto);
        logger.info("Created house with ID: {}", house.getId());
        return houseMapper.toDto(houseRepository.save(house));
    }

    @CacheEvict(value = "houses", key = "#houseId")
    @Transactional
    public void delete(Long houseId) throws HouseNotFoundException {
        logger.info("Deleting house with ID: {}", houseId);
        if (houseRepository.existsById(houseId)) {
            houseRepository.deleteById(houseId);
            logger.info("Deleted house with ID: {}", houseId);
        } else {
            logger.info("There is no house with ID: {}", houseId);
            throw new HouseNotFoundException("There is no house with ID: " + houseId);
        }
    }

    @CacheEvict(value = "houses", key = "#houseId")
    @Transactional
    public HouseDto update(Long houseId, HouseDto houseDto) throws HouseNotFoundException {
        logger.info("Updating house with ID: {}", houseId);
        return houseRepository.findById(houseId)
                .map(existingHouse -> {
                    House houseToUpdate = houseMapper.toEntity(houseDto);
                    houseToUpdate.setId(houseId);
                    logger.info("Updating house with ID: {}", houseId);
                    return houseMapper.toDto(houseRepository.save(houseToUpdate));
                }).orElseThrow(() -> {
                    logger.info("There is no house with ID: {}", houseId);
                    return new HouseNotFoundException("There is no house with ID: " + houseId);
                });
    }

    @Cacheable(value = "houses", key = "#page + '-' + #size")
    @Transactional
    public Page<HouseDto> getAll(int page, int size) {
        logger.info("Fetching all houses - Page: {}, Size: {}", page, size);
        Pageable pageable = Pageable.ofSize(size).withPage(page);
        return houseRepository.findAll(pageable)
                .map(houseMapper::toDto);
    }

    @Cacheable(value = "houses", key = "#houseId")
    @Transactional
    public HouseDto getById(Long houseId) throws HouseNotFoundException {
        logger.info("Fetching house with ID: {}", houseId);
        return houseRepository.findById(houseId)
                .map(houseMapper::toDto)
                .orElseThrow(() -> {
                    logger.error("House with ID: {} does not exist", houseId);
                    return new HouseNotFoundException("House with ID " + houseId + " does not exist");
                });
    }
}
