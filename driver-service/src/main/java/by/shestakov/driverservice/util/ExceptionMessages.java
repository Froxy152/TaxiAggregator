package by.shestakov.driverservice.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ExceptionMessages {
    public static final String BAD_REQUEST_MESSAGE = "This %s was deleted";
    public static final String CONFLICT_MESSAGE = "This %s already exists";
    public static final String NOT_FOUND_MESSAGE = "%s with id %d not found";
}
