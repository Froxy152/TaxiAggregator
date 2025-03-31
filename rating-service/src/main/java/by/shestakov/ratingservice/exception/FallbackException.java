package by.shestakov.ratingservice.exception;


public class FallbackException extends RuntimeException {
    public FallbackException(String msg) {
        super(msg);
    }
}

