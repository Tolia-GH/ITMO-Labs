package se.ifmo.model;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.*;

import java.time.ZonedDateTime;

@Data
@NoArgsConstructor
@JacksonXmlRootElement(localName = "success")
public class SuccessResponse {

    @JacksonXmlProperty(localName = "code")
    private int code;

    @JacksonXmlProperty(localName = "message")
    private String message;

    @JacksonXmlProperty(localName = "time")
    private ZonedDateTime time;

    public SuccessResponse(int code, String message) {
        this.code = code;
        this.message = message;
        this.time = ZonedDateTime.now();
    }

}
