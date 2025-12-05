package se.ifmo.service1ejb.dao.model;

import jakarta.persistence.*;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlTransient;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import se.ifmo.service1ejb.dao.adapter.IntArrayToListConverter;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "starship", schema = "soa_lab2")
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Starship implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @XmlTransient//Xml ignore
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "coordinate_id", referencedColumnName = "id", nullable = false)
    private Coordinates coordinates;

    @Column(name = "creation_date", nullable = false)
    private ZonedDateTime creationDate;

    // 使用自定义 AttributeConverter 映射 PostgreSQL INTEGER[] 与 List<Long>
    @Convert(converter = IntArrayToListConverter.class)
    @Column(name = "space_marine_id_list", columnDefinition = "integer[] DEFAULT '{}'")
    private List<Long> spaceMarineIDs;
}
