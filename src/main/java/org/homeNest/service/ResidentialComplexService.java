package org.homeNest.service;

import lombok.AllArgsConstructor;
import org.homeNest.dto.ResidentialComplexCreateDto;
import org.homeNest.dto.ResidentialComplexDto;
import org.homeNest.entity.Apartment;
import org.homeNest.entity.ResidentialComplex;
import org.homeNest.exception.ResidentialComplexNotFoundException;
import org.homeNest.mapper.ResidentialComplexMapper;
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

@Service
@Transactional(readOnly = true)
@AllArgsConstructor
public class ResidentialComplexService {

    private static final Logger logger = LoggerFactory.getLogger(ResidentialComplexService.class);

    private final ResidentialComplexMapper residentialComplexMapper;
    private final ResidentialComplexRepository residentialComplexRepository;
    private final ApartmentRepository apartmentRepository;

    @CacheEvict(value = "residentialComplexes", allEntries = true)
    @Transactional
    public ResidentialComplexDto create(ResidentialComplexCreateDto residentialComplexCreateDto) {
//        ResidentialComplex residentialComplex = residentialComplexMapper.toEntity(residentialComplexDto);
//        logger.info("Create ResidentialComplex: {}", residentialComplex);
//        List<Apartment> apartments = residentialComplex.getApartments();
//        if (apartments != null && !apartments.isEmpty()) {
//            apartments.forEach(apartment -> apartment.setResidentialComplex(residentialComplex));
//            apartmentRepository.saveAll(apartments);
//        }
//        return residentialComplexMapper.toDto(residentialComplexRepository.save(residentialComplex));
//
//
//        ResidentialComplex residentialComplex = residentialComplexMapper.toEntity(residentialComplexCreateDto);
//        logger.info("Create ResidentialComplex: {}", residentialComplex);
//        residentialComplex = residentialComplexRepository.save(residentialComplex);
//
//        for (Apartment apartment : residentialComplex.getApartments()) {
//            apartment.setResidentialComplex(residentialComplex);
//            apartmentRepository.save(apartment);
//        }
//
//        return residentialComplexMapper.toDto(residentialComplex);

        ResidentialComplex residentialComplex = residentialComplexMapper.toEntity(residentialComplexCreateDto);
        logger.info("Create ResidentialComplex: {}", residentialComplex);
        for (Apartment apartment : residentialComplex.getApartments()) {
            apartment.setResidentialComplex(residentialComplex);
        }

        ResidentialComplex savedResidentialComplex = residentialComplexRepository.save(residentialComplex);

        return residentialComplexMapper.toDto(savedResidentialComplex);
    }

    @CacheEvict(value = "residentialComplexes", key = "#residentialComplexId")
    @Transactional
    public void delete(Long residentialComplexId) throws ResidentialComplexNotFoundException {
        if (residentialComplexRepository.existsById(residentialComplexId)) {
            residentialComplexRepository.deleteById(residentialComplexId);
            logger.info("Delete ResidentialComplex: {}", residentialComplexId);
        } else {
            logger.error("ResidentialComplex not found: {}", residentialComplexId);
            throw new ResidentialComplexNotFoundException("ResidentialComplex with Id " + residentialComplexId + " not found");
        }
    }

    @CacheEvict(value = "residentialComplexes", key = "#residentialComplexId")
    @Transactional
    public ResidentialComplexDto update(Long residentialComplexId, ResidentialComplexDto residentialComplexDto) {
        logger.info("Update ResidentialComplex with Id: {}", residentialComplexId);
        return residentialComplexRepository.findById(residentialComplexId)
                .map(residentialComplex -> {
                    ResidentialComplex updatedResidentialComplex = residentialComplexMapper.toEntity(residentialComplexDto);
                    updatedResidentialComplex.setId(residentialComplexId);
                    updatedResidentialComplex.setApartments(residentialComplex.getApartments());
                    return residentialComplexMapper.toDto(residentialComplexRepository.save(updatedResidentialComplex));
                }).orElseThrow(() -> {
                    logger.error("ResidentialComplex with Id {} not found: ", residentialComplexId);
                    return new ResidentialComplexNotFoundException("ResidentialComplex with Id " + residentialComplexId + " not found");
                });
    }
    @Cacheable(value = "residentialComplexes", key = "#page + '-' + #size")
    @Transactional
    public Page<ResidentialComplexDto> getAll(int page, int size) {
        logger.info("Get All ResidentialComplexes");
        Pageable pageable = Pageable.ofSize(size).withPage(page);
        return residentialComplexRepository.findAll(pageable)
                .map(residentialComplexMapper::toDto);
    }

    @Cacheable(value = "residentialComplexById", key = "#residentialComplexId")
    @Transactional
    public ResidentialComplexDto getById(Long residentialComplexId) {
        logger.info("Get ResidentialComplex with Id: {}", residentialComplexId);
        return residentialComplexRepository.findById(residentialComplexId)
                .map(residentialComplexMapper::toDto)
                .orElseThrow(() -> {
                    logger.error("ResidentialComplex with Id {} not found: ", residentialComplexId);
                    return new ResidentialComplexNotFoundException("ResidentialComplex with Id " + residentialComplexId + " not found");
                });
    }

    @Cacheable(value = "residentialComplexByName", key = "#residentialComplexName")
    @Transactional
    public ResidentialComplexDto getByName(String residentialComplexName) {
        logger.info("Get ResidentialComplex with Name: {}", residentialComplexName);
        return residentialComplexRepository.findByName(residentialComplexName)
                .map(residentialComplexMapper::toDto)
                .orElseThrow(() -> {
                    logger.error("ResidentialComplex with name {} not found: ", residentialComplexName);
                    return new ResidentialComplexNotFoundException("ResidentialComplex with name " + residentialComplexName + " not found");
                });
    }
}
