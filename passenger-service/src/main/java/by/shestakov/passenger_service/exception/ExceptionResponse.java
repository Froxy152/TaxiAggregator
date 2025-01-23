package by.shestakov.passenger_service.exception;

import lombok.*;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
@Builder
public class ExceptionResponse {
    String message;
    HttpStatus status;
    LocalDateTime time;
}
