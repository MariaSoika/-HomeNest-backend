package org.homeNest.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.homeNest.dto.UserCreateDto;
import org.homeNest.dto.UserDto;
import org.homeNest.entity.User;
import org.homeNest.exception.UserNotFoundException;
import org.homeNest.security.JwtService;
import org.homeNest.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private static final Logger log = LoggerFactory.getLogger(UserController.class);


    private final UserService userService;
    private final JwtService jwtService;

    @PostMapping
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserCreateDto userCreateDto) {
        UserDto createdUser = userService.create(userCreateDto);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) throws UserNotFoundException {
        userService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDto> updateUser(
            @PathVariable Long id,
            @Valid @RequestBody UserDto userDto) throws UserNotFoundException {
        UserDto updatedUser = userService.update(id, userDto);
        return ResponseEntity.ok(updatedUser);
    }

    @GetMapping
    public ResponseEntity<Page<UserDto>> getAllUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<UserDto> users = userService.getAll(page, size);
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long id) throws UserNotFoundException {
        UserDto user = userService.getById(id);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/me")
    public ResponseEntity<?> getUser(@RequestHeader("Authorization") String authHeader) {
        try {
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                log.warn("Authorization header is missing or invalid: {}", authHeader);
                return ResponseEntity.badRequest().body("Missing or invalid Authorization header");
            }

            String token = authHeader.replace("Bearer ", "");
            String username = jwtService.extractUserName(token);
            User user = userService.getByUsername(username);

            return ResponseEntity.ok("UserId: " + user.getId());

        } catch (io.jsonwebtoken.JwtException e) {
            log.error("Invalid or expired token: {}", e.getMessage(), e);
            return ResponseEntity.status(401).body("Invalid or expired token");

        } catch (UsernameNotFoundException e) {
            log.error("User not found for extracted username", e);
            return ResponseEntity.status(404).body("User not found");

        } catch (Exception e) {
            log.error("Unexpected error in getUser()", e);
            return ResponseEntity.status(500).body("Internal server error");
        }
    }



}