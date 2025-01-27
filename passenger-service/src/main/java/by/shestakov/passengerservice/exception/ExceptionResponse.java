package by.shestakov.passengerservice.exception;

import lombok.Builder;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.Map;


@Builder
public record ExceptionResponse (
    HttpStatus status,
    LocalDateTime time,
    Map<String, String> errors) {
}

