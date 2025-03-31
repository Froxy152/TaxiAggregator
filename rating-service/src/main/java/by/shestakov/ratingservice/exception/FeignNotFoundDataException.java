package by.shestakov.ratingservice.exception;

public class FeignNotFoundDataException extends RuntimeException {
    public FeignNotFoundDataException(String message) {
        super(message);
    }
}
