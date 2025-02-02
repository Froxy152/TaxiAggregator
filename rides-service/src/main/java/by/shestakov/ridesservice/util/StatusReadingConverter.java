package by.shestakov.ridesservice.util;

import by.shestakov.ridesservice.entity.Status;
import org.springframework.core.convert.converter.Converter;
import org.bson.json.StrictJsonWriter;
import org.springframework.data.convert.ReadingConverter;

@ReadingConverter
public class StatusReadingConverter implements Converter<Integer, Status> {
    @Override
    public Status convert(Integer source) {
        if(source == null || source < 0 || source >= Status.values().length){
            throw new IllegalArgumentException("Invalid status value" + source);
        }
        return Status.values()[source];
    }
}
