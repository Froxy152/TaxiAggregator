package by.shestakov.ratingservice.exception;

import by.shestakov.ratingservice.util.ExceptionMessage;
import feign.RetryableException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({MethodArgumentTypeMismatchException.class, IllegalArgumentException.class})
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

    @ExceptionHandler({FeignClientNotFoundDataException.class, DataNotFoundException.class})
    public ResponseEntity<ExceptionResponse> handleFeignClientNotFoundDataException(Exception e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ExceptionResponse.builder()
                .status(HttpStatus.NOT_FOUND)
                .time(LocalDateTime.now())
                .errors(Map.of("message", e.getMessage()))
                .build());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionResponse> handleNotValidArgument(MethodArgumentNotValidException e) {
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
