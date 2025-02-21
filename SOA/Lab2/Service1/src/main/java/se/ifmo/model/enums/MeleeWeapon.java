package se.ifmo.model.enums;

import jakarta.xml.bind.annotation.XmlEnum;
import jakarta.xml.bind.annotation.XmlType;

@XmlEnum
@XmlType(name = "meleeWeapon")
public enum MeleeWeapon {
    CHAIN_SWORD,
    POWER_BLADE,
    LIGHTING_CLAW
}
