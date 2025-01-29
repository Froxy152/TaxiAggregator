package by.shestakov.driverservice.exception.car;

public class CarNumberAlreadyException extends RuntimeException{
    public CarNumberAlreadyException(String message){
        super(message);
    }
}
