package com.blps.lab1.databaseJPA;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "reviews", schema = "business_logic")
public class ReviewsJPA {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "movie_id")
    private Integer movie_id;
    @Column(name = "author_id")
    private Integer author_id;
    @Column(name = "content")
    private String content;
}
