package org.example.coursework.service;

import lombok.AllArgsConstructor;
import org.example.coursework.dto.UserCreateDto;
import org.example.coursework.dto.UserDto;
import org.example.coursework.entity.User;
import org.example.coursework.mapper.UserMapper;
import org.example.coursework.repository.ApartmentRepository;
import org.example.coursework.repository.UserRepository;
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
public class UserService {

    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final ApartmentRepository apartmentRepository;

    @CacheEvict(value = "users", allEntries = true)
    @Transactional
    public UserDto create(UserCreateDto userCreateDto) {
        User user = userMapper.toEntity(userCreateDto);
        return userMapper.toDto(userRepository.save(user));
    }

    @CacheEvict(value = "users", key = "#userID")
    @Transactional
    public void delete(Long userID) {
        if (userRepository.existsById(userID)) {
            userRepository.deleteById(userID);
        } else {
            throw new IllegalArgumentException("User with ID " + userID + " does not exist");
        }
    }

    @CacheEvict(value = "users", key = "#userID")
    @Transactional
    public UserDto update(Long userID, UserDto userDto) {
        return userRepository.findById(userID)
                .map(existingUser -> {
                    User updateUser = userMapper.toEntity(userDto);
                    updateUser.setID(userID);
                    updateUser.setFavorite(userDto.favorite().stream()
                            .map(apartmentDto -> apartmentRepository.getReferenceById(apartmentDto.ID()))
                            .collect(Collectors.toList()));
                    return userMapper.toDto(userRepository.save(updateUser));
                })
                .orElseThrow(() -> new IllegalArgumentException("User with ID " + userID + " does not exist"));
    }

    @Cacheable(value = "users", key = "#page + '-' + #size")
    public Page<UserDto> getAll(int page, int size) {
        Pageable pageable = Pageable.ofSize(size).withPage(page);
        return userRepository.findAll(pageable)
                .map(userMapper::toDto);
    }

    @Cacheable(value = "users", key = "#userID")
    public UserDto getById(Long userID) {
        return userRepository.findById(userID)
                .map(userMapper::toDto)
                .orElseThrow(() -> new IllegalArgumentException("User with ID " + userID + " does not exist"));
    }
}