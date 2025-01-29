package by.shestakov.driverservice.exception.driver;

public class DriverWasDeletedException extends RuntimeException{
    public DriverWasDeletedException(String message){
        super(message);
    }
}
