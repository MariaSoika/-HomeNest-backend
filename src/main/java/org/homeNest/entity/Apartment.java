package org.homeNest.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.homeNest.enums.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "apartments", uniqueConstraints = {
        @UniqueConstraint(columnNames = "title")
})
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Apartment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "selling_status")
    private SellingStatus sellingStatus;

    @ManyToOne
    @JoinColumn(name = "residential_complex_id", nullable = false)
    private ResidentialComplex residentialComplex;

   @Column(name = "photo")
    private String photos;

    @NotBlank(message = "Title is mandatory")
    @Column(name = "title", unique = true)
    private String title;

    @Positive(message = "Price must be greater than 0")
    @Column(name = "price")
    private double price;

    @Positive(message = "Area must be greater than 0")
    @Column(name = "area")
    private double area;

    @Min(value = 1, message = "Rooms must be at least 1")
    @Column(name = "rooms")
    private int rooms;

    @Min(value = 1, message = "Floor must be at least 1")
    @Column(name = "floor")
    private int floor;

    @NotBlank(message = "Address is mandatory")
    @Column(name = "address", unique = true)
    private String address;

    @Enumerated(EnumType.STRING)
    @Column(name = "property_availability_status")
    private PropertyAvailabilityStatus propertyAvailabilityStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "property_verification_status")
    private PropertyVerificationStatus propertyVerificationStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "heating_type")
    private HeatingType heatingType;

    @Enumerated(EnumType.STRING)
    @Column(name = "water_supply_type")
    private WaterSupplyType waterSupplyType;

    @Enumerated(EnumType.STRING)
    @Column(name = "state_of_repair")
    private StateOfRepair stateOfRepair;

    @Column(name = "description")
    private String description;

}
