package by.shestakov.ridesservice.exception;

public class FallbackException extends RuntimeException {
    public FallbackException(String msg) {
        super(msg);
    }
}
