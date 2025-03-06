package se.ifmo.dao.adapter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import jakarta.persistence.PersistenceException;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@Converter(autoApply = true)
public class ZonedDateTimeConverter implements AttributeConverter<ZonedDateTime, Instant> {

    @Override
    public Instant convertToDatabaseColumn(ZonedDateTime zonedDateTime) {
        return zonedDateTime != null ? zonedDateTime.toInstant() : null;
    }

    @Override
    public ZonedDateTime convertToEntityAttribute(Instant instant) {
        return instant != null ? ZonedDateTime.ofInstant(instant, ZoneId.systemDefault()) : null;
    }

    // 用于处理数据库返回的日期字符串格式
    public ZonedDateTime convertFromDatabaseString(String dbValue) {
        try {
            // 自定义解析规则，确保解析时能够处理各种时间格式
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSS Z");
            return ZonedDateTime.parse(dbValue, formatter);
        } catch (DateTimeParseException e) {
            throw new PersistenceException("Failed to parse date from database: " + dbValue, e);
        }
    }
}

