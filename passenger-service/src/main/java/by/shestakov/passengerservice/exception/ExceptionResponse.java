package by.shestakov.passengerservice.exception;

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
