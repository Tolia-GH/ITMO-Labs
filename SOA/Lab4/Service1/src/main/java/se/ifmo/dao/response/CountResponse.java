package se.ifmo.dao.response;

import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
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
public class CountResponse {
    @XmlElement
    private int code;
    @XmlElement
    private int count;
    @Temporal(TemporalType.TIMESTAMP)
    @XmlElement
    @XmlJavaTypeAdapter(ZonedDateTimeAdapter.class)
    private ZonedDateTime time;
}
