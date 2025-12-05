package se.ifmo.service1ejb.dao.adapter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Converter
public class IntArrayToListConverter implements AttributeConverter<List<Long>, Integer[]> {

    @Override
    public Integer[] convertToDatabaseColumn(List<Long> attribute) {
        if (attribute == null) return new Integer[]{};
        return attribute.stream().map(Long::intValue).toArray(Integer[]::new);
    }

    @Override
    public List<Long> convertToEntityAttribute(Integer[] dbData) {
        if (dbData == null) return new ArrayList<>();
        return Arrays.stream(dbData).map(Integer::longValue).toList();
    }
}
