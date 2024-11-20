package org.example.coursework.service;

import lombok.AllArgsConstructor;
import org.example.coursework.dto.UserCreateDto;
import org.example.coursework.dto.UserDto;
import org.example.coursework.entity.User;
import org.example.coursework.exception.UserNotFoundException;
import org.example.coursework.mapper.UserMapper;
import org.example.coursework.repository.ApartmentRepository;
import org.example.coursework.repository.UserRepository;
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
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final ApartmentRepository apartmentRepository;

    @CacheEvict(value = "users", allEntries = true)
    @Transactional
    public UserDto create(UserCreateDto userCreateDto) {
        User user = userMapper.toEntity(userCreateDto);
        logger.info("Created user with ID: {}", user.getID());
        return userMapper.toDto(userRepository.save(user));
    }

    @CacheEvict(value = "users", key = "#userID")
    @Transactional
    public void delete(Long userID) throws UserNotFoundException {
        if (userRepository.existsById(userID)) {
            userRepository.deleteById(userID);
            logger.info("Deleted user with ID: {}", userID);
        } else {
            logger.info("User with ID: {} does not exist", userID);
            throw new UserNotFoundException("User with ID " + userID + " does not exist");
        }
    }

    @CacheEvict(value = "users", key = "#userID")
    @Transactional
    public UserDto update(Long userID, UserDto userDto) throws UserNotFoundException {
        logger.info("Updating user with ID: {}", userID);
        return userRepository.findById(userID)
                .map(existingUser -> {
                    User updateUser = userMapper.toEntity(userDto);
                    updateUser.setID(userID);
                    updateUser.setFavorite(userDto.favorite().stream()
                            .map(apartmentDto -> apartmentRepository.getReferenceById(apartmentDto.ID()))
                            .collect(Collectors.toList()));
                    return userMapper.toDto(userRepository.save(updateUser));
                })
                .orElseThrow(() -> {
                    logger.error("User with ID: {} does not exist", userID);
                    return new UserNotFoundException("User with ID " + userID + " does not exist");
                });
    }

    @Cacheable(value = "users", key = "#page + '-' + #size")
    @Transactional
    public Page<UserDto> getAll(int page, int size) {
        logger.info("Getting all users with page {} and size {}", page, size);
        Pageable pageable = Pageable.ofSize(size).withPage(page);
        return userRepository.findAll(pageable)
                .map(userMapper::toDto);
    }

    @Cacheable(value = "users", key = "#userID")
    @Transactional
    public UserDto getById(Long userID) throws UserNotFoundException {
        logger.info("Getting user with ID: {}", userID);
        return userRepository.findById(userID)
                .map(userMapper::toDto)
                .orElseThrow(() -> {
                    logger.error("User with ID: {} does not exist", userID);
                    return new UserNotFoundException("User with ID " + userID + " does not exist");
                });
    }
}