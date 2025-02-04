package by.shestakov.ridesservice.util.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ValidationConstant {
    public static final String DRIVER_ID_MANDATORY = "driverId is mandatory";
    public static final String PASSENGER_ID_MANDATORY = "passengerId is mandatory";
    public static final String PICKUP_ADDRESS_MANDATORY = "pickUpAddress is mandatory";
    public static final String DESTINATION_ADDRESS_MANDATORY = "destinationAddress is mandatory";
    public static final String STATUS_MANDATORY = "status is mandatory";
}
