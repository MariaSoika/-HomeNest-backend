package org.example.coursework.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "orders_reports")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class OrderReport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Order is mandatory")
    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @NotBlank(message = "Description is mandatory")
    @Column(name = "report_description")
    private String description;
}
