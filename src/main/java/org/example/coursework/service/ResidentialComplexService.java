package org.example.coursework.service;

import lombok.AllArgsConstructor;
import org.example.coursework.dto.ResidentialComplexDto;
import org.example.coursework.entity.ResidentialComplex;
import org.example.coursework.exception.ResidentialComplexNotFoundException;
import org.example.coursework.mapper.ResidentialComplexMapper;
import org.example.coursework.repository.ApartmentRepository;
import org.example.coursework.repository.ResidentialComplexRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@AllArgsConstructor
public class ResidentialComplexService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    private final ResidentialComplexMapper residentialComplexMapper;
    private final ResidentialComplexRepository residentialComplexRepository;
    private final ApartmentRepository apartmentRepository;

    @CacheEvict(value = "residentialComplexes", allEntries = true)
    @Transactional
    public ResidentialComplexDto create(ResidentialComplexDto residentialComplexDto) {
        ResidentialComplex residentialComplex = residentialComplexMapper.toEntity(residentialComplexDto);
        logger.info("Create ResidentialComplex: {}", residentialComplex);
        return residentialComplexMapper.toDto(residentialComplexRepository.save(residentialComplex));
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
                    updatedResidentialComplex.setApartments(residentialComplexDto.apartmentIDS().stream()
                            .map(apartmentDto -> apartmentRepository.getReferenceById(apartmentDto.ID()))
                            .collect(Collectors.toList()));
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

    @Cacheable(value = "residentialComplexes", key = "#page + '-' + #size")
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
}
