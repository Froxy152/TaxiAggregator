package by.shestakov.passengerservice.exception;

public class PassengerWasDeletedException extends RuntimeException{
   public PassengerWasDeletedException(String message){
        super(message);
    }
}
