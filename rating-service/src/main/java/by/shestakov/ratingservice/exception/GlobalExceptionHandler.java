package by.shestakov.ratingservice.exception;

import java.time.LocalDateTime;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ExceptionResponse> handleMethodMismatch(Exception e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ExceptionResponse.builder()
                .status(HttpStatus.BAD_REQUEST)
                .time(LocalDateTime.now())
                .errors(Map.of("message", e.getMessage()))
                .build());
    }

    @ExceptionHandler(OnlyOneCommentOnRideException.class)
    public ResponseEntity<ExceptionResponse> handleOnlyOneComment(Exception e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ExceptionResponse.builder()
                .status(HttpStatus.CONFLICT)
                .time(LocalDateTime.now())
                .errors(Map.of("message", e.getMessage()))
                .build());
    }

    @ExceptionHandler(TestException.class)
    public ResponseEntity<ExceptionResponse> handleTestException(Exception e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ExceptionResponse.builder()
                .status(HttpStatus.CONFLICT)
                .time(LocalDateTime.now())
                .errors(Map.of("message", e.getMessage()))
                .build());
    }

}
