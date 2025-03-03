package by.shestakov.ridesservice.exception;

import by.shestakov.ridesservice.util.constant.ExceptionMessage;

public class DataNotFoundException extends RuntimeException {
    public DataNotFoundException(String data, Long id) {
        super(ExceptionMessage.NOT_FOUND_MESSAGE);
    }
}
