package by.shestakov.ridesservice.exception;


import by.shestakov.ridesservice.util.constant.ExceptionMessage;

public class DriverWithoutCarException extends RuntimeException {
    public DriverWithoutCarException() {
        super(ExceptionMessage.DRIVER_WITHOUT_CAR);
    }
}
