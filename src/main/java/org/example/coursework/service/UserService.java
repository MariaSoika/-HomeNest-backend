package org.example.coursework.service;

import lombok.AllArgsConstructor;
import org.example.coursework.dto.UserCreateDto;
import org.example.coursework.dto.UserDto;
import org.example.coursework.entity.User;
import org.example.coursework.mapper.UserMapper;
import org.example.coursework.repository.ApartmentRepository;
import org.example.coursework.repository.UserRepository;
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

    @Transactional
    public UserDto create(UserCreateDto userCreateDto) {
        User user = userMapper.toEntity(userCreateDto);
        return userMapper.toDto(userRepository.save(user));
    }

    @Transactional
    public void delete(Long userID) {
        if (userRepository.existsById(userID)) {
            userRepository.deleteById(userID);
        } else {
            throw new IllegalArgumentException("User with ID " + userID + " does not exist");
        }
    }

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

    public List<UserDto> getAll() {
        return userRepository.findAll().stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList());
    }

    public UserDto getById(Long userID) {
        return userRepository.findById(userID)
                .map(userMapper::toDto)
                .orElseThrow(() -> new IllegalArgumentException("User with ID " + userID + " does not exist"));
    }
}