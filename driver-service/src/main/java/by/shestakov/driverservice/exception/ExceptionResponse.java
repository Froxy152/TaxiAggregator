package by.shestakov.driverservice.exception;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.Map;

@Builder
public record ExceptionResponse (
        HttpStatus status,
        Map<String,String> errors,
        LocalDateTime time
){

}

