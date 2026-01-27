package se.ifmo.dao.model;

import jakarta.persistence.*;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlTransient;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Entity
@Table(name = "starship")
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement(name = "starship")
@XmlAccessorType(XmlAccessType.FIELD)
public class Starship {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @XmlTransient//Xml ignore
    private Long id;

    @Column(name = "name")
    private String name;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "coordinate_id", referencedColumnName = "id")
    @XmlElement(name = "coordinates")
    private Coordinates coordinates;

    @ElementCollection
    @XmlElement(name = "space_marine_id_list")
    private List<Long> spaceMarineIDs;
}
