package by.shestakov.passengerservice.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ExceptionConstants {
    public static final String NOT_FOUND_MESSAGE = "Passenger with id = %d  not found";
    public static final String CONFLICT_MESSAGE = "Passenger with %s or %s already exists";
    public static final String BAD_REQUEST_MESSAGE = "Passenger with id = %d is deleted";
}
