package by.shestakov.ratingservice.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ExceptionMessage {
    public static final String ONLY_ONE_MESSAGE = "User can send 1 review";
    public static final String NOT_FOUND_MESSAGE = "Data not found";
    public static final String SERVICE_UNAVAILABLE = "%s service is unavailable";
    public static final String ILLEGAL_EXCEPTION_MESSAGE = "Invalid Enum value : %s";
    public static final String DRIVER_SERVICE_UNAVAILABLE_CIRCUIT_BREAKER = "Driver service is unavailable, try later";
    public static final String PASSENGER_SERVICE_UNAVAILABLE_CIRCUIT_BREAKER = "Passenger service is unavailable, try later";

}
