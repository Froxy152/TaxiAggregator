package by.shestakov.ridesservice.exception;

import by.shestakov.ridesservice.util.constant.ExceptionMessage;

public class DataNotFoundException extends RuntimeException {
    public DataNotFoundException() {
        super(ExceptionMessage.NOT_FOUND_MESSAGE);
    }
}
