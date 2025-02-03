package by.shestakov.ridesservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<ExceptionResponse> handleNotFoundException(RideNotFoundException e){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                ExceptionResponse.builder()
                        .time(LocalDateTime.now())
                        .status(HttpStatus.NOT_FOUND)
                        .errors(Map.of("messages", e.getMessage()))
                        .build()
        );
    }

}
