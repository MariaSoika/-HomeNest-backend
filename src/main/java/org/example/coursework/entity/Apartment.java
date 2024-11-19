package org.example.coursework.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.Collection;

@Entity
@Table(name = "apartments")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Apartment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long ID;

    @NotBlank(message = "Photo is mandatory")
    @Column(name = "photo")
    private String photo;

    @NotBlank(message = "Title is mandatory")
    @Column(name = "title")
    private String title;

    @NotBlank(message = "Description is mandatory")
    @Column(name = "price")
    private double price;

    @NotBlank(message = "Area is mandatory")
    @Column(name = "area")
    private double area;

    @NotBlank(message = "Rooms is mandatory")
    @Column(name = "rooms")
    private int rooms;

    @NotBlank(message = "Floor is mandatory")
    @Column(name = "floor")
    private int floor;

    @NotBlank(message = "Address is mandatory")
    @Column(name = "address")
    private String address;

    @NotBlank(message = "Status is mandatory")
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;

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

