package by.shestakov.ratingservice.exception;

import by.shestakov.ratingservice.util.ExceptionMessage;

public class DataNotFoundException extends RuntimeException {
    public DataNotFoundException() {
        super(ExceptionMessage.NOT_FOUND_MESSAGE);
    }
}
