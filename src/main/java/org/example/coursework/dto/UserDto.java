package org.example.coursework.dto;

import org.example.coursework.enums.Role;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

/**
 * DTO for {@link org.example.coursework.entity.User}
 */
public record UserDto(long ID, Role role, String password, List<ApartmentDto> favorite) implements Serializable {

}