package by.shestakov.ratingservice.util;


import lombok.AccessLevel;
import lombok.NoArgsConstructor;


@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ValidationConstants {
    public static final String RIDE_ID_IS_MANDATORY = "Ride id is mandatory";
    public static final String PASSENGER_ID_IS_MANDATORY = "Passenger id is mandatory";
    public static final String DRIVER_ID_IS_MANDATORY = "Driver id is mandatory";
    public static final String MARK_IS_MANDATORY = "Mark is mandatory";
}
