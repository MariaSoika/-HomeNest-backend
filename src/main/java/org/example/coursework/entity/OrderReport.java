package org.example.coursework.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
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
public class OrderReport extends Order{

}
