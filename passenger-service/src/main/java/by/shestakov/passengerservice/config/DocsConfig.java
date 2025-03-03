package by.shestakov.passengerservice.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DocsConfig {
    @Bean
    public OpenAPI swaggerConfig() {
        return new OpenAPI()
                .info(
                        new Info().title("this is the REST API for passenger-service")
                                .version("0.0.1")
                );
    }
}