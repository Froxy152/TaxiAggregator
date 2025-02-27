package by.shestakov.passengerservice.util;


import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ExceptionConstants {
    public static final String NOT_FOUND_MESSAGE = "Passenger with id = %d  not found";
    public static final String CONFLICT_MESSAGE = "Passenger with %s or %s already exists";
}