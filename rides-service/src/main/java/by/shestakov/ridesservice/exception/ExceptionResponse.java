package by.shestakov.ridesservice.exception;

import java.time.LocalDateTime;
import java.util.Map;
import lombok.Builder;
import org.springframework.http.HttpStatus;

@Builder
public record ExceptionResponse(
        Map<String, String> errors,
        LocalDateTime time,
        HttpStatus status
) {
}
