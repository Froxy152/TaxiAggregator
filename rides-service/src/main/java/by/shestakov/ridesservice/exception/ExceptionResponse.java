package by.shestakov.ridesservice.exception;

import lombok.Builder;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.Map;

@Builder
public record ExceptionResponse(
        Map<String, String> errors,
        LocalDateTime time,
        HttpStatus status
) {


}
