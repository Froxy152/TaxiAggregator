package by.shestakov.driverservice.exception;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.Map;

@Builder
@Data
public class ExceptionResponse {
    String message;
    HttpStatus httpStatus;
    LocalDateTime time;
    Map<String,String> errors;
}
