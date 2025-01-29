package by.shestakov.driverservice.exception.driver;

public class DriverAlreadyExistsException extends RuntimeException{
    public DriverAlreadyExistsException(String message){
        super(message);
    }
}
