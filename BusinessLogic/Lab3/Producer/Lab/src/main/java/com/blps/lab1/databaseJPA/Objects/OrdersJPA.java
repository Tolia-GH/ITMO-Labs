package com.blps.lab1.databaseJPA.Objects;

import com.blps.lab1.databaseJPA.OrderStatus;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

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
    @Enumerated(EnumType.STRING)
    private OrderStatus status;
    @Column(name = "creation_time")
    private LocalDateTime creation_time;
}
