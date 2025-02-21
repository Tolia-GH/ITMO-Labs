package se.ifmo.model.entity;

import jakarta.persistence.*;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Data;
import se.ifmo.model.enums.MeleeWeapon;

import java.time.ZonedDateTime;

@Data
@Entity
@XmlRootElement
public class SpaceMarine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Embedded
    private Coordinates coordinates;

    @Column(name = "creation_date", updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private ZonedDateTime creationDate;

    @Column(name = "health")
    private Integer health;

    @Column(name = "heart_count")
    private Integer heartCount;

    private Float height;

    @Enumerated
    @Column(name = "melee_wepon")
    private MeleeWeapon meleeWeapon;

    @Embedded
    private Chapter chapter;
}
