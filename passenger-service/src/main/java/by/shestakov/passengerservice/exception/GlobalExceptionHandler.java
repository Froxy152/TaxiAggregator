package by.shestakov.passengerservice.exception;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(PassengerAlreadyExistsException.class)
    public ResponseEntity<ExceptionResponse> handleAlreadyExistsException(PassengerAlreadyExistsException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ExceptionResponse.builder()
                .errors(Map.of("message", e.getMessage()))
                .status(HttpStatus.CONFLICT)
                .time(LocalDateTime.now())
                .build());
    }

    @ExceptionHandler(PassengerNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleNotFoundException(PassengerNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ExceptionResponse.builder()
                .errors(Map.of("message", e.getMessage()))
                .time(LocalDateTime.now())
                .status(HttpStatus.NOT_FOUND)
                .build());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionResponse> handleValidationException(MethodArgumentNotValidException e) {
        Map<String, String> errors = new HashMap<>();
        e.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorResponse = error.getDefaultMessage();
            errors.put(fieldName, errorResponse);
        });
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ExceptionResponse.builder()
                .status(HttpStatus.BAD_REQUEST)
                .time(LocalDateTime.now())
                .errors(errors)
                .build());

    }
}
