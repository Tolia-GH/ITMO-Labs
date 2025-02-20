package se.ifmo.resource.model;

import jakarta.persistence.Embeddable;

@Embeddable
public class Coordinates {
    private Integer x;
    private Double y;
}
