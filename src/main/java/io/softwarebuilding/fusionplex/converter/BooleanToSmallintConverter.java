package io.softwarebuilding.fusionplex.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class BooleanToSmallintConverter implements AttributeConverter<Boolean, Integer> {

    @Override
    public Integer convertToDatabaseColumn(final Boolean attribute) {
        if (attribute == null) {
            return null;
        }

        return attribute ? 1 : 0;
    }

    @Override
    public Boolean convertToEntityAttribute(final Integer dbColumnValue) {

        if (dbColumnValue == null) {
            return null;
        }

        return dbColumnValue != 0;
    }
}
