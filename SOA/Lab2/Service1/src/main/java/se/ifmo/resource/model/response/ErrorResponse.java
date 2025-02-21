package se.ifmo.resource.model.response;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement(name = "error")
@XmlType(propOrder = {"code", "message", "time"})
public class ErrorResponse {
    @XmlElement
    private int code;
    @XmlElement
    private String message;
    @XmlElement
    private ZonedDateTime time;
}
