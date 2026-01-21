package se.ifmo.dao.model;

import jakarta.persistence.*;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import se.ifmo.dao.model.enums.MeleeWeapon;

@Data
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement(name = "space_marine")
@XmlAccessorType(XmlAccessType.FIELD)
public class NewSpaceMarine {
    private String name;
    @XmlElement(name = "coordinates")
    private Coordinates coordinates;
    private Integer health;
    @XmlElement(name = "heart_count")
    private Integer heartCount;
    private Float height;
    @Enumerated(EnumType.STRING)
    @Column(name = "melee_weapon", columnDefinition = "soa_lab2.melee_weapon")
    @XmlElement(name = "melee_weapon")
    private MeleeWeapon meleeWeapon;
    @XmlElement(name = "chapter")
    private Chapter chapter;
}
