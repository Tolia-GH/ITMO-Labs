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
@XmlRootElement(name = "space_marine_list")
@XmlAccessorType(XmlAccessType.FIELD)
public class SpaceMarineResponse {
    @XmlElement(name = "space_marine")
    private List<SpaceMarine> spaceMarines;
}
