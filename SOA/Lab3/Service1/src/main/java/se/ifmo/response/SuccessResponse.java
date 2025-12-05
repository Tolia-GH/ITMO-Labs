package se.ifmo.response;

import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.xml.bind.annotation.*;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import se.ifmo.dao.adapter.ZonedDateTimeAdapter;

import java.time.ZonedDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement(name = "success")
@XmlAccessorType(XmlAccessType.FIELD)
public class SuccessResponse {
    @XmlElement
    private int code;
    @XmlElement
    private String message;
    @Temporal(TemporalType.TIMESTAMP)
    @XmlElement
    @XmlJavaTypeAdapter(ZonedDateTimeAdapter.class)
    private ZonedDateTime time;
}