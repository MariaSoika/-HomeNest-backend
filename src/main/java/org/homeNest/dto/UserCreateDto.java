package org.homeNest.dto;

import org.homeNest.enums.Role;
import org.homeNest.entity.User;

import java.io.Serializable;

/**
 * DTO for {@link User}
 */
public record UserCreateDto(Role role, String password) implements Serializable {
}