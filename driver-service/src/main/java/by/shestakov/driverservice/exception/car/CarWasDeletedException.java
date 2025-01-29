package by.shestakov.driverservice.exception.car;

public class CarWasDeletedException extends RuntimeException{
    public CarWasDeletedException(String message){
        super(message);
    }
}
