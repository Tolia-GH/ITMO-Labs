package com.blps.lab1.databaseJPA;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "tickets", schema = "business_logic")
public class TicketsJPA {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "movie_id")
    private Integer movie_id;
    @Column(name = "amount")
    private Integer amount;
    @Column(name = "price")
    private double price;
}
