package se.ifmo.model;


import javax.xml.bind.annotation.XmlRootElement;
import java.time.ZonedDateTime;

@XmlRootElement(name = "success")
public class SuccessResponse {

    private int code;
    private String message;
    private ZonedDateTime time;

    public SuccessResponse() {
        this.time = ZonedDateTime.now();
    }

    public SuccessResponse(int code, String message) {
        this.code = code;
        this.message = message;
        this.time = ZonedDateTime.now();
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ZonedDateTime getTime() {
        return time;
    }

    public void setTime(ZonedDateTime time) {
        this.time = time;
    }

}
