package by.shestakov.ridesservice.util;

import by.shestakov.ridesservice.entity.Status;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.WritingConverter;

@WritingConverter
public class StatusWritingConverter implements Converter<Status, Integer> {
    @Override
    public Integer convert(Status source) {
        return source.ordinal();
    }
}
