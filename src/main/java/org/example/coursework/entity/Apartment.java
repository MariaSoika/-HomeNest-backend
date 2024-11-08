package org.example.coursework.entity;

import jakarta.persistence.*;
import lombok.*;

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

    @Column(name = "photo")
    private String photo;

    @Column(name = "title")
    private String title;

    @Column(name = "price")
    private double price;

    @Column(name = "area")
    private double area;

    @Column(name = "rooms")
    private int rooms;

    @Column(name = "floor")
    private int floor;

    @Column(name = "address")
    private String address;

    @Column(name = "status")
    private Status status;

    public enum Status {
        AVAILABLE("available"),
        SOLD("sold"),
        ON_VIEW("on view");

        private final String title;

        Status(String title) {
            this.title = title;
        }

        public String getTitle() {
            return title;
        }
    }
}

