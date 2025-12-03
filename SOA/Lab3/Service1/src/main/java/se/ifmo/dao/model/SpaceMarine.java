package se.ifmo.dao.model;

import jakarta.persistence.*;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Check;
import org.hibernate.annotations.Type;
import se.ifmo.dao.adapter.ZonedDateTimeAdapter;
import se.ifmo.dao.adapter.ZonedDateTimeConverter;
import se.ifmo.dao.model.enums.MeleeWeapon;

import java.time.ZonedDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "space_marine", schema = "soa_lab2")
@Entity
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class SpaceMarine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, length = 255)
    private String name;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "coordinate_id", referencedColumnName = "id", nullable = false)
    private Coordinates coordinates;

    @Column(name = "creation_date", nullable = false)
    @Convert(converter = ZonedDateTimeConverter.class)
    @XmlElement
    @XmlJavaTypeAdapter(ZonedDateTimeAdapter.class)
    private ZonedDateTime creationDate;

    @PrePersist
    protected void onCreate() {
        if (creationDate == null) {
            creationDate = ZonedDateTime.now();
        }
    }

    @Column(name = "health", nullable = false)
    @Check(constraints = "health >= 1")
    private Integer health;

    @Column(name = "heart_count", nullable = false)
    @Check(constraints = "heart_count BETWEEN 1 AND 3")
    private Integer heartCount;

    @Column(name = "height", nullable = false)
    private Float height;

    @Enumerated(EnumType.STRING)
    @Column(name = "melee_weapon")
    private MeleeWeapon meleeWeapon;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "chapter_id", referencedColumnName = "id")
    @XmlElement
    private Chapter chapter;
}
