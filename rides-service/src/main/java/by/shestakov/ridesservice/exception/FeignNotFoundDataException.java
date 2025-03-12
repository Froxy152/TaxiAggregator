package by.shestakov.ridesservice.exception;


public class FeignNotFoundDataException extends RuntimeException {

    public FeignNotFoundDataException(String message) {
        super(message);
    }
}
