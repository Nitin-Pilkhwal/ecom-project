package com.demo.project.nitin.ecommerce.utils.annotations;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Converter
public class StringSetConverter implements AttributeConverter<Set<String>, String> {

    @Override
    public String convertToDatabaseColumn(Set<String> attribute) {
        return (attribute == null || attribute.isEmpty()) ? null : String.join(",", attribute);
    }

    @Override
    public Set<String> convertToEntityAttribute(String dbData) {
        return (dbData == null || dbData.isBlank()) ? new HashSet<>() :
                new HashSet<>(Arrays.asList(dbData.split(",")));
    }
}
