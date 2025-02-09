package org.example.coursework.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.example.coursework.enums.HeatingType;
import org.example.coursework.enums.StateOfRepair;
import org.example.coursework.enums.WaterSupplyType;

@Entity
@Table(name = "apartments", uniqueConstraints = {
        @UniqueConstraint(columnNames = "title"),
        @UniqueConstraint(columnNames = "address")
})
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Apartment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long ID;

    @ManyToOne
    @JoinColumn(name = "residential_complex_id", nullable = false)
    private ResidentialComplex residentialComplex;

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

    @Min(value = 0, message = "Floor must be at least 0")
    @Column(name = "floor")
    private int floor;

    @NotBlank(message = "Address is mandatory")
    @Column(name = "address", unique = true)
    private String address;

    @NotNull(message = "Status is mandatory")
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;

    @Enumerated(EnumType.STRING)
    @Column(name = "heating_type")
    private HeatingType heatingType;

    @Enumerated(EnumType.STRING)
    @Column(name = "water_supply_type")
    private WaterSupplyType waterSupplyType;

    @Enumerated(EnumType.STRING)
    @Column(name = "state_of_repair")
    private StateOfRepair stateOfRepair;

    @Getter
    public enum Status {
        AVAILABLE("available"),
        SOLD("sold"),
        ON_VIEW("on view");

        private final String title;

        Status(String title) {
            this.title = title;
        }
    }
}
