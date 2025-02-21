package se.ifmo.model.entity;

import jakarta.persistence.Embeddable;

@Embeddable
public class Coordinates {
    private Integer x;
    private Double y;
}
