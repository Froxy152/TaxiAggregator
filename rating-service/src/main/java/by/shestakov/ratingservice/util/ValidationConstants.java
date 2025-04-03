package by.shestakov.ratingservice.util;


import lombok.AccessLevel;
import lombok.NoArgsConstructor;


@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ValidationConstants {
    public static final String RIDE_ID_IS_MANDATORY = "Ride id is mandatory";
    public static final String PASSENGER_ID_IS_MANDATORY = "Passenger id is mandatory";
    public static final String DRIVER_ID_IS_MANDATORY = "Driver id is mandatory";
    public static final String RATE_IS_MANDATORY = "Rate is mandatory";
    public static final String RATE_CANNOT_BE_LOWER = "Rate cannot be lower than 0.0";
    public static final String RATE_CANNOT_BE_HIGHER = "Rate cannot be higher than 5.0";
    public static final String RATED_BY_IS_MANDATORY = "RatedBy is mandatory";
}
