package by.shestakov.driverservice.exception;

import java.time.LocalDateTime;
import java.util.Map;
import lombok.Builder;
import org.springframework.http.HttpStatus;

@Builder
public record ExceptionResponse(
        HttpStatus status,
        Map<String, String> errors,
        LocalDateTime time
) {
}

