package io.github.up2jakarta.csv.test.bean;

import jakarta.persistence.AttributeConverter;

public class TestConverter implements AttributeConverter<Long, String> {

    @Override
    public String convertToDatabaseColumn(Long attribute) {
        return String.valueOf(attribute);
    }

    @Override
    public Long convertToEntityAttribute(String dbData) {
        return Long.parseLong(dbData);
    }

}
