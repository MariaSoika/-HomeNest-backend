package org.example.coursework.entity;

import jakarta.persistence.*;
import lombok.*;
import org.example.coursework.enums.RentStatus;

import java.time.LocalDate;

@Entity
@Table(name = "rents")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Rent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "apartment_id", nullable = false)
    private Apartment apartment;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(name = "price", nullable = false)
    private Double price;

    @Column(name = "deposit")
    private Double deposit;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private RentStatus status;

    @Column(name = "description")
    private String description;

//    @OneToMany(mappedBy = "rent", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<Payment> payments = new ArrayList<>();
}
