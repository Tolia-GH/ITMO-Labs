package se.ifmo.model.entity;

import jakarta.persistence.*;
import jakarta.xml.bind.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "chapter")
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement
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
