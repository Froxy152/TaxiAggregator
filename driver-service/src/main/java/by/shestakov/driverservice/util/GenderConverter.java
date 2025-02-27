package by.shestakov.driverservice.util;

import by.shestakov.driverservice.entity.Gender;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class GenderConverter implements AttributeConverter<Gender, Integer> {

    @Override
    public Integer convertToDatabaseColumn(Gender gender) {
        if (gender == null) {
            return null;
        }
        return gender.getCode();
    }

    @Override
    public Gender convertToEntityAttribute(Integer integer) {
        if (integer == null) {
            return null;
        }
        return Gender.fromValue(integer);
    }
}
