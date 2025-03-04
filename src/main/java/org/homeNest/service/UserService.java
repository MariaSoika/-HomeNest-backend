package org.homeNest.service;

import lombok.RequiredArgsConstructor;
import org.homeNest.dto.UserCreateDto;
import org.homeNest.dto.UserDto;
import org.homeNest.entity.User;
import org.homeNest.enums.Role;
import org.homeNest.exception.UserNotFoundException;
import org.homeNest.mapper.UserMapper;
import org.homeNest.repository.ApartmentRepository;
import org.homeNest.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository repository;
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    //
    private final UserMapper userMapper;
    //    private final UserRepository userRepository;
    private final ApartmentRepository apartmentRepository;

    /**
     * Сохранение пользователя
     *
     * @return сохраненный пользователь
     */
    public User save(User user) {
        return repository.save(user);
    }


    /**
     * Создание пользователя
     *
     * @return созданный пользователь
     */
    public User create(User user) {
        if (repository.existsByUsername(user.getUsername())) {
            // Заменить на свои исключения
            throw new RuntimeException("Пользователь с таким именем уже существует");
        }

        if (repository.existsByEmail(user.getEmail())) {
            throw new RuntimeException("Пользователь с таким email уже существует");
        }

        return save(user);
    }

    /**
     * Получение пользователя по имени пользователя
     *
     * @return пользователь
     */
    public User getByUsername(String username) {
        return repository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден"));

    }

    /**
     * Получение пользователя по имени пользователя
     * <p>
     * Нужен для Spring Security
     *
     * @return пользователь
     */
    public UserDetailsService userDetailsService() {
        return this::getByUsername;
    }

    /**
     * Получение текущего пользователя
     *
     * @return текущий пользователь
     */
    public User getCurrentUser() {
        // Получение имени пользователя из контекста Spring Security
        var username = SecurityContextHolder.getContext().getAuthentication().getName();
        return getByUsername(username);
    }


    /**
     * Выдача прав администратора текущему пользователю
     * <p>
     * Нужен для демонстрации
     */
    @Deprecated
    public void getAdmin() {
        var user = getCurrentUser();
        user.setRole(Role.ROLE_ADMIN);
        save(user);
    }


    @CacheEvict(value = "users", allEntries = true)
    @Transactional
    public UserDto create(UserCreateDto userCreateDto) {
        User user = userMapper.toEntity(userCreateDto);
        logger.info("Created user with ID: {}", user.getId());
        return userMapper.toDto(repository.save(user));
    }

    @CacheEvict(value = "users", key = "#userID")
    @Transactional
    public void delete(Long userID) throws UserNotFoundException {
        if (repository.existsById(userID)) {
            repository.deleteById(userID);
            logger.info("Deleted user with ID: {}", userID);
            repository.flush();
        } else {
            logger.info("User with ID: {} does not exist", userID);
            throw new UserNotFoundException("User with ID " + userID + " does not exist");
        }
    }

    @CacheEvict(value = "users", key = "#userID")
    @Transactional
    public UserDto update(Long userID, UserDto userDto) throws UserNotFoundException {
        logger.info("Updating user with ID: {}", userID);
        return repository.findById(userID)
                .map(existingUser -> {
                    User updateUser = userMapper.toEntity(userDto);
                    updateUser.setId(userID);
//                    updateUser.setFavorite(userDto.favorite().stream()
//                            .map(apartmentDto -> apartmentRepository.getReferenceById(apartmentDto.ID()))
//                            .collect(Collectors.toList()));
                    return userMapper.toDto(repository.save(updateUser));
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
        return repository.findAll(pageable)
                .map(userMapper::toDto);
    }

    @Cacheable(value = "users", key = "#userID")
    @Transactional
    public UserDto getById(Long userID) throws UserNotFoundException {
        logger.info("Getting user with ID: {}", userID);
        return repository.findById(userID)
                .map(userMapper::toDto)
                .orElseThrow(() -> {
                    logger.error("User with ID: {} does not exist", userID);
                    return new UserNotFoundException("User with ID " + userID + " does not exist");
                });
    }

}


//package org.example.coursework.service;
//
//import lombok.AllArgsConstructor;
//import org.example.coursework.dto.UserCreateDto;
//import org.example.coursework.dto.UserDto;
//import org.example.coursework.entity.User;
//import org.example.coursework.exception.UserNotFoundException;
//import org.example.coursework.mapper.UserMapper;
//import org.example.coursework.repository.ApartmentRepository;
//import org.example.coursework.repository.UserRepository;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.cache.annotation.CacheEvict;
//import org.springframework.cache.annotation.Cacheable;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.stream.Collectors;
//
//@Service
//@Transactional(readOnly = true)
//@AllArgsConstructor
//public class UserService {
//
//    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
//
//    private final UserMapper userMapper;
//    private final UserRepository userRepository;
//    private final ApartmentRepository apartmentRepository;
//
//    @CacheEvict(value = "users", allEntries = true)
//    @Transactional
//    public UserDto create(UserCreateDto userCreateDto) {
//        User user = userMapper.toEntity(userCreateDto);
//        logger.info("Created user with ID: {}", user.getID());
//        return userMapper.toDto(userRepository.save(user));
//    }
//
//    @CacheEvict(value = "users", key = "#userID")
//    @Transactional
//    public void delete(Long userID) throws UserNotFoundException {
//        if (userRepository.existsById(userID)) {
//            userRepository.deleteById(userID);
//            logger.info("Deleted user with ID: {}", userID);
//            userRepository.flush();
//        } else {
//            logger.info("User with ID: {} does not exist", userID);
//            throw new UserNotFoundException("User with ID " + userID + " does not exist");
//        }
//    }
//
//    @CacheEvict(value = "users", key = "#userID")
//    @Transactional
//    public UserDto update(Long userID, UserDto userDto) throws UserNotFoundException {
//        logger.info("Updating user with ID: {}", userID);
//        return userRepository.findById(userID)
//                .map(existingUser -> {
//                    User updateUser = userMapper.toEntity(userDto);
//                    updateUser.setID(userID);
//                    updateUser.setFavorite(userDto.favorite().stream()
//                            .map(apartmentDto -> apartmentRepository.getReferenceById(apartmentDto.ID()))
//                            .collect(Collectors.toList()));
//                    return userMapper.toDto(userRepository.save(updateUser));
//                })
//                .orElseThrow(() -> {
//                    logger.error("User with ID: {} does not exist", userID);
//                    return new UserNotFoundException("User with ID " + userID + " does not exist");
//                });
//    }
//
//    @Cacheable(value = "users", key = "#page + '-' + #size")
//    @Transactional
//    public Page<UserDto> getAll(int page, int size) {
//        logger.info("Getting all users with page {} and size {}", page, size);
//        Pageable pageable = Pageable.ofSize(size).withPage(page);
//        return userRepository.findAll(pageable)
//                .map(userMapper::toDto);
//    }
//
//    @Cacheable(value = "users", key = "#userID")
//    @Transactional
//    public UserDto getById(Long userID) throws UserNotFoundException {
//        logger.info("Getting user with ID: {}", userID);
//        return userRepository.findById(userID)
//                .map(userMapper::toDto)
//                .orElseThrow(() -> {
//                    logger.error("User with ID: {} does not exist", userID);
//                    return new UserNotFoundException("User with ID " + userID + " does not exist");
//                });
//    }
//}