package com.blps.lab3.databaseJPA.Objects;

import com.blps.lab3.databaseJPA.CommentStatus;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "comments", schema = "business_logic")
public class CommentJPA {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "movie_id")
    private Integer movie_id;
    @Column(name = "author_id")
    private Integer author_id;
    @Column(name = "content")
    private String content;
    @Enumerated(EnumType.STRING)
    private CommentStatus status;
}
