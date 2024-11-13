package org.example.coursework.entity;

import org.example.coursework.enums.Role;

import java.io.Serializable;

/**
 * DTO for {@link User}
 */
public record UserCreateDto(Role role, int password) implements Serializable {
}