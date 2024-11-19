package org.example.coursework.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "appointments_reports")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class AppointmentReport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Appointment is mandatory")
    @ManyToOne
    @JoinColumn(name = "appointment_id")
    private Appointment appointment;

    @NotBlank(message = "Description is mandatory")
    @Column(name = "report_description")
    private String description;
}
