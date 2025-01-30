package by.shestakov.driverservice.exception;

import by.shestakov.driverservice.exception.car.CarNotFoundException;
import by.shestakov.driverservice.exception.car.CarNumberAlreadyException;
import by.shestakov.driverservice.exception.driver.DriverAlreadyExistsException;
import by.shestakov.driverservice.exception.driver.DriverNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({
            CarNumberAlreadyException.class,
            DriverAlreadyExistsException.class
    })
    public ResponseEntity<ExceptionResponse> handleDriverAlReadyExistsException(Exception e) {
        return ResponseEntity.status(HttpStatus.CONFLICT.value()).body(
                ExceptionResponse.builder()
                        .errors(Map.of("message", e.getMessage()))
                        .status(HttpStatus.CONFLICT)
                        .time(LocalDateTime.now())
                        .build()
        );
    }

    @ExceptionHandler({
            DriverNotFoundException.class,
            CarNotFoundException.class
    })
    public ResponseEntity<ExceptionResponse> handleDriverNotFoundException(Exception e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND.value()).body(
                ExceptionResponse.builder()
                        .errors(Map.of("message", e.getMessage()))
                        .status(HttpStatus.NOT_FOUND)
                        .time(LocalDateTime.now())
                        .build()
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionResponse> handleValidationException(MethodArgumentNotValidException e) {
        Map<String, String> errors = new HashMap<>();
        e.getBindingResult().getAllErrors().forEach((error) ->
        {
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
