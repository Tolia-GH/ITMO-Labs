package se.ifmo.model.entity;

import jakarta.persistence.*;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import se.ifmo.model.adapter.ZonedDateTimeAdapter;
import se.ifmo.model.enums.MeleeWeapon;

import java.time.ZonedDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class NewSpaceMarine {
    private String name;
    @XmlElement
    private Coordinates coordinates;
    private Integer health;
    private Integer heartCount;
    private Float height;
    @Enumerated(EnumType.STRING)
    @Type(type = "pgsql_enum")
    private MeleeWeapon meleeWeapon;
    @XmlElement
    private Chapter chapter;
}
