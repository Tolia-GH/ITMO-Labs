package se.ifmo.service1ejb.dao.model.enums;

import jakarta.xml.bind.annotation.XmlEnum;
import jakarta.xml.bind.annotation.XmlType;

import java.io.Serializable;

@XmlEnum
@XmlType(name = "meleeWeapon")
public enum MeleeWeapon {
    CHAIN_SWORD,
    POWER_BLADE,
    LIGHTING_CLAW
}
