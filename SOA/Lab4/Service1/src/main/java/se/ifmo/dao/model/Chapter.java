package se.ifmo.dao.model;

import jakarta.persistence.*;
import jakarta.xml.bind.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "chapter", schema = "soa_lab2")
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement(name = "chapter")
@XmlAccessorType(XmlAccessType.FIELD)
public class Chapter {
    @Id
    @GeneratedValue
    @XmlTransient//Xml ignore
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "world")
    private String world;
}
