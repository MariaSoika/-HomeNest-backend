package org.homeNest.entity;

import jakarta.persistence.*;
import lombok.*;
import org.homeNest.enums.BuildingConstructionType;

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

     @Column(name = "photo")
    private String photos;

    @OneToMany(mappedBy = "residentialComplex", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @ToString.Exclude
    private List<Apartment> apartments = new ArrayList<>();


}
