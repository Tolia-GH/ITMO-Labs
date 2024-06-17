package com.blps.lab1.databaseJPA;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "orders", schema = "business_logic")
public class OrdersJPA {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "user_id")
    private Integer user_id;
    @Column(name = "ticket_id")
    private Integer ticket_id;
    @Column(name = "is_paid")
    private Boolean is_paid;
}
