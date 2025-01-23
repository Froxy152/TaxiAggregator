package by.shestakov.passenger_service.exception;

public class BadRequestException extends RuntimeException{
   public BadRequestException(String message){
        super(message);
    }
}
