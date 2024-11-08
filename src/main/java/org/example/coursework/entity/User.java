package org.example.coursework.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.example.coursework.enums.*;

import java.util.ArrayList;

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

    @Column(name = "role")
    private Role role;

    @Column(name = "password")
    private int password;

    @Column(name = "favorite")
    private ArrayList<Apartment> favorite;
}


