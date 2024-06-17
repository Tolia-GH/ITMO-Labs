package com.blps.lab1.databaseJPA;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "movies", schema = "business_logic")
public class MoviesJPA {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "name")
    private String name;
    @Column(name = "description")
    private String description;
    @Column(name = "rating")
    private Float rating;
    @Column(name = "ratecount")
    private Integer rateCount;
}
