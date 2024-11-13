package org.example.coursework.dto;

import org.example.coursework.enums.Role;

import java.io.Serializable;

/**
 * DTO for {@link org.example.coursework.entity.User}
 */
public record UserCreateDto(Role role, int password) implements Serializable {
}