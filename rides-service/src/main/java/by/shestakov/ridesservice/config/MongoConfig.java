package by.shestakov.ridesservice.config;

import by.shestakov.ridesservice.util.converter.StatusReadingConverter;
import by.shestakov.ridesservice.util.converter.StatusWritingConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;

import java.util.List;

@Configuration
public class MongoConfig {
    @Bean
    public MongoCustomConversions customConversions() {
        return new MongoCustomConversions(List.of(
                new StatusReadingConverter(),
                new StatusWritingConverter()));
    }
}
