package org.homeNest.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.homeNest.enums.HeatingType;
import org.homeNest.enums.PropertyAvailabilityStatus;
import org.homeNest.enums.StateOfRepair;
import org.homeNest.enums.WaterSupplyType;

@Entity
@Table(name = "houses")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class House {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank(message = "Photo is mandatory")
    @Column(name = "photo")
    private String photo;

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

    @Min(value = 0, message = "Storeys must be at least 1")
    @Column(name = "storeys")
    private int storeys;

    @NotBlank(message = "Address is mandatory")
    @Column(name = "address", unique = true)
    private String address;

    @NotNull(message = "Status is mandatory")
    @Enumerated(EnumType.STRING)
    @Column(name = "propertyAvailabilityStatus")
    private PropertyAvailabilityStatus propertyAvailabilityStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "heating_type")
    private HeatingType heatingType;

    @Enumerated(EnumType.STRING)
    @Column(name = "water_supply_type")
    private WaterSupplyType waterSupplyType;

    @Enumerated(EnumType.STRING)
    @Column(name = "state_of_repair")
    private StateOfRepair stateOfRepair;

}
