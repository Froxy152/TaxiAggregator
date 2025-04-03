package by.shestakov.ridesservice.util.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ExceptionMessage {
    public static final String NOT_FOUND_MESSAGE = "Ride not found";
    public static final String ILLEGAL_EXCEPTION = "Invalid status code: %d";
    public static final String DRIVER_WITHOUT_CAR = "This driver without active car";
    public static final String SERVICE_UNAVAILABLE = "%s service is unavailable";
}
