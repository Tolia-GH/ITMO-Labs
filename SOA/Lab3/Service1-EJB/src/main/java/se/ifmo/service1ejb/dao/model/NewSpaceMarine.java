package se.ifmo.service1ejb.dao.model;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import se.ifmo.service1ejb.dao.model.enums.MeleeWeapon;

import java.io.Serializable;


@Data
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class NewSpaceMarine implements Serializable {
    private String name;
    @XmlElement
    private Coordinates coordinates;
    private Integer health;
    private Integer heartCount;
    private Float height;
    @Enumerated(EnumType.STRING)
    private MeleeWeapon meleeWeapon;
    @XmlElement
    private Chapter chapter;
}
