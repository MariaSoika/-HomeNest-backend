package org.homeNest.dto;

import org.homeNest.enums.Role;
import org.homeNest.entity.User;

import java.io.Serializable;
import java.util.List;

/**
 * DTO for {@link User}
 */
public record UserDto(long id, Role role, String password, List<ApartmentDto> favorite, String username, String email) implements Serializable {

}