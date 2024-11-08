package org.example.coursework.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Entity
@Table(name = "appointments")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long ID;

    @Column(name = "user")
    private User user;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private Apartment apartment;

    private LocalDate appointmentDate;
}
