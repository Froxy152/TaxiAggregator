package by.shestakov.passengerservice.exception;

import lombok.*;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.Map;

@Data
@Builder
public class ExceptionResponse {
    String message;
    HttpStatus status;
    LocalDateTime time;
    Map<String, String> errors;
}
