package by.shestakov.passengerservice.exception;

public class PassengerNotFoundException extends RuntimeException{
    public PassengerNotFoundException(String message){
        super(message);
    }
}
