package se.ifmo.dao.response;

import jakarta.xml.bind.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import se.ifmo.dao.model.SpaceMarine;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement(name = "res")
@XmlAccessorType(XmlAccessType.FIELD)
public class SpaceMarineResponse {
    @XmlElement(name = "SpaceMarine")
    private List<SpaceMarine> spaceMarines;
}
