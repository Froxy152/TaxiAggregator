package by.shestakov.passengerservice.exception;

import org.springframework.context.annotation.EnableLoadTimeWeaving;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;


@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AlreadyExistsException.class)
    public ResponseEntity<ExceptionResponse> handleAlreadyExistsException(AlreadyExistsException e){
       return ResponseEntity.status(HttpStatus.CONFLICT).body(ExceptionResponse.builder()
                        .status(HttpStatus.CONFLICT)
                        .time(LocalDateTime.now())
                        .message(e.getMessage())
               .build());
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleNotFoundException(NotFoundException e){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ExceptionResponse.builder()
                        .message(e.getMessage())
                        .time(LocalDateTime.now())
                        .status(HttpStatus.NOT_FOUND)
                .build());
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ExceptionResponse> handleBadRequestException(BadRequestException e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ExceptionResponse.builder()
                        .status(HttpStatus.BAD_REQUEST)
                        .time(LocalDateTime.now())
                        .message(e.getMessage())
                .build());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionResponse> handleValidationException(MethodArgumentNotValidException e){
        Map<String, String> errors = new HashMap<>();
        e.getBindingResult().getAllErrors().forEach((error) ->
        { String fieldName = ((FieldError) error).getField();
            String errorResponse = error.getDefaultMessage();
            errors.put(fieldName,errorResponse);
        });
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ExceptionResponse.builder()
                       .status(HttpStatus.BAD_REQUEST)
                       .time(LocalDateTime.now())
                       .message(e.getMessage())
                        .errors(errors)
               .build());

    }
}