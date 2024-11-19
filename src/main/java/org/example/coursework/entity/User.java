package org.example.coursework.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.example.coursework.dto.ApartmentDto;
import org.example.coursework.enums.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long ID;

    @NotBlank(message = "Role is mandatory")
    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Role role;

    @NotBlank(message = "Password is mandatory")
    @Column(name = "password")
    private String password;

    @ManyToMany
    @JoinTable(
            name = "user_favorites",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "apartment_id")
    )
    @ToString.Exclude
    private List<Apartment> favorite;
}


