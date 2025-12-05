package se.ifmo.service1ejb.dao.adapter;


import jakarta.xml.bind.annotation.adapters.XmlAdapter;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class ZonedDateTimeAdapter extends XmlAdapter<String, ZonedDateTime> {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ISO_ZONED_DATE_TIME;

    @Override
    public ZonedDateTime unmarshal(String s) throws Exception {
        return ZonedDateTime.parse(s, formatter);
    }

    @Override
    public String marshal(ZonedDateTime z) throws Exception {
        return z.format(formatter);
    }
}
