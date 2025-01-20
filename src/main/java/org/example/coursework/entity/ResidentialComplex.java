package org.example.coursework.entity;

import jakarta.persistence.*;
import lombok.*;
import org.example.coursework.enums.BuildingConstructionType;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table (name = "residential_complexes")
@Entity
public class ResidentialComplex {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name = "address")
    private String address;

    @Enumerated(EnumType.STRING)
    @Column(name = "building_construction_type")
    private BuildingConstructionType buildingConstructionType;

    @Column(name = "description")
    private String description;

    @ElementCollection
    @CollectionTable(name = "residential_complex_photos", joinColumns = @JoinColumn(name = "residential_complex_id"))
    @Column(name = "photo")
    private List<String> photos = new ArrayList<>();


    @OneToMany(mappedBy = "residentialComplex", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @ToString.Exclude
    private List<Apartment> apartments = new ArrayList<>();
}
