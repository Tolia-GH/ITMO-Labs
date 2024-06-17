package com.blps.lab1.databaseJPA;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "favourites", schema = "business_logic")
public class FavouritesJPA {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "user_id")
    private Integer user_id;
    @Column(name = "movie_id")
    private Integer movie_id;
}
