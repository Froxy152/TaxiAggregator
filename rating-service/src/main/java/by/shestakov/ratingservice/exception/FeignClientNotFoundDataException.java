package by.shestakov.ratingservice.exception;

public class FeignClientNotFoundDataException extends RuntimeException {
    public FeignClientNotFoundDataException(String message) {
        super(message);
    }
}
