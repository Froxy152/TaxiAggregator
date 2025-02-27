package by.shestakov.passengerservice.exception;

import java.time.LocalDateTime;
import java.util.Map;
import lombok.Builder;
import org.springframework.http.HttpStatus;



@Builder
public record ExceptionResponse(
        HttpStatus status,
        LocalDateTime time,
        Map<String, String> errors) {
}
