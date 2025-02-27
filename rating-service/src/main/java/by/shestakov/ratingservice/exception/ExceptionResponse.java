package by.shestakov.ratingservice.exception;

import java.time.LocalDateTime;
import java.util.Map;
import lombok.Builder;
import org.springframework.http.HttpStatus;

@Builder
public record ExceptionResponse(
        LocalDateTime time,
        HttpStatus status,
        Map<String, String> errors
) {
}
