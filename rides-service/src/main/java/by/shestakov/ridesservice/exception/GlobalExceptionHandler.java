package by.shestakov.ridesservice.exception;

import by.shestakov.ridesservice.util.constant.ExceptionMessage;
import feign.RetryableException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({FeignNotFoundDataException.class, DataNotFoundException.class})
    public ResponseEntity<ExceptionResponse> handleNotFoundException(Exception e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                ExceptionResponse.builder()
                        .time(LocalDateTime.now())
                        .status(HttpStatus.NOT_FOUND)
                        .errors(Map.of("message", e.getMessage()))
                        .build()
        );
    }

    @ExceptionHandler(DriverWithoutCarException.class)
    public ResponseEntity<ExceptionResponse> handleDriverWithoutCarException(Exception e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                ExceptionResponse.builder()
                        .time(LocalDateTime.now())
                        .status(HttpStatus.BAD_REQUEST)
                        .errors(Map.of("message", e.getMessage()))
                        .build()
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionResponse> handleNotValidException(MethodArgumentNotValidException  e) {
        Map<String, String> errors = new HashMap<>();
        e.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorResponse = error.getDefaultMessage();
            errors.put(fieldName, errorResponse);
        });
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                ExceptionResponse.builder()
                        .status(HttpStatus.BAD_REQUEST)
                        .time(LocalDateTime.now())
                        .errors(errors)
                        .build());
    }

    @ExceptionHandler(RetryableException.class)
    public ResponseEntity<ExceptionResponse> handleFeignRetryException(Exception e) {
        String[] fullMessage = e.getMessage().split("/");
        String service = StringUtils.capitalize(fullMessage[5].substring(0, fullMessage[5].length() - 1));

        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(ExceptionResponse.builder()
                .status(HttpStatus.SERVICE_UNAVAILABLE)
                .time(LocalDateTime.now())
                .errors(Map.of("message", ExceptionMessage.SERVICE_UNAVAILABLE.formatted(service)))
                .build());
    }



}
