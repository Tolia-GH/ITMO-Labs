package se.ifmo.service1ejb.dao.adapter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@Converter(autoApply = true)
public class ZonedDateTimeConverter implements AttributeConverter<ZonedDateTime, Timestamp> {

    // 允许 ISO 格式以及一些常见的变体格式
    private static final DateTimeFormatter[] formatters = new DateTimeFormatter[]{
            DateTimeFormatter.ISO_ZONED_DATE_TIME,
            DateTimeFormatter.ofPattern("yyyy/MM/dd a hh:mm"),  // 处理中文上午/下午
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSS Z"), // 另一种可能的格式
            DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSSXXX")  // 适应时区的格式
    };

    @Override
    public Timestamp convertToDatabaseColumn(ZonedDateTime zonedDateTime) {
        if (zonedDateTime != null) {
            return Timestamp.from(zonedDateTime.toInstant());  // 将 ZonedDateTime 转换为 Timestamp
        }
        return null;
    }

    @Override
    public ZonedDateTime convertToEntityAttribute(Timestamp dbData) {
        if (dbData != null) {
            // 将 Timestamp 转换为 ZonedDateTime
            Instant instant = dbData.toInstant();
            return ZonedDateTime.ofInstant(instant, ZoneId.systemDefault());
        }
        return null;
    }

}
