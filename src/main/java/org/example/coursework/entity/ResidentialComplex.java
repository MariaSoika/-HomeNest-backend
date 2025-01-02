package org.example.coursework.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table (name = "residentialComplexes")
@Entity
public class ResidentialComplex {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long Id;

    @Column(name = "name")
    private String name;

    @Column(name = "address")
    private String address;

    @Column(name = "technologyOfBuilding")
    private boolean technologyOfBuilding;

    @Column(name = "description")
    private String description;

    @ToString.Exclude
    @ElementCollection
    @Column(name = "photos")
    private List<String> photos = new ArrayList<>();


    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "residential_complex_apartment",
            joinColumns = @JoinColumn(name = "rc_id"),
            inverseJoinColumns = @JoinColumn(name = "apartment_id")
    )
    @ToString.Exclude
    private List<Apartment> apartments = new ArrayList<>();
}
