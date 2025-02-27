package by.shestakov.ridesservice.util.converter;

import by.shestakov.ridesservice.entity.Status;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;

@ReadingConverter
public class StatusReadingConverter implements Converter<Integer, Status> {
    @Override
    public Status convert(Integer source) {
        return Status.fromValue(source);
    }
}
