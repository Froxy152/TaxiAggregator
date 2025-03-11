package testConstant;

import by.shestakov.ridesservice.dto.PathDto;
import by.shestakov.ridesservice.dto.request.RideRequest;
import by.shestakov.ridesservice.dto.request.RideStatusRequest;
import by.shestakov.ridesservice.dto.request.RideUpdateRequest;
import by.shestakov.ridesservice.dto.response.DriverResponse;
import by.shestakov.ridesservice.dto.response.PassengerResponse;
import by.shestakov.ridesservice.dto.response.RideResponse;
import by.shestakov.ridesservice.dto.response.RoutingResponse;
import by.shestakov.ridesservice.entity.Car;
import by.shestakov.ridesservice.entity.Driver;
import by.shestakov.ridesservice.entity.Gender;
import by.shestakov.ridesservice.entity.Passenger;
import by.shestakov.ridesservice.entity.Ride;
import by.shestakov.ridesservice.entity.Status;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public final class TestConstant {
    public static final String TEST_ID = "ID";
    public static final String TEST_INVALID_ID = "invalid_id";
    public static final Long TEST_DRIVER_ID = 1L;
    public static final Long TEST_PASSENGER_ID = 1L;
    public static final String TEST_PICKUP_ADDRESS = "55.48632,28.765492";
    public static final String TEST_PICKUP_ADDRESS_FOR_UPDATE = "44.48632,28.765492";
    public static final String TEST_DESTINATION_ADDRESS = "55.521048,28.663522";
    public static final String TEST_DESTINATION_ADDRESS_FOR_UPDATE = "68.521048,28.663522";
    public static final Double TEST_DISTANCE = 1300.0;
    public static final LocalDateTime TEST_TIME = LocalDateTime.now();
    public static final Integer TEST_DURING = 2;
    public static final BigDecimal TEST_PRICE = BigDecimal.valueOf(12.2);
    public static final Status TEST_STATUS = Status.CREATED;
    public static final Driver TEST_DRIVER =
            new Driver(TEST_DRIVER_ID, "ILYA", "SHESTAKOV", "test@example.com", "+375295035659", Gender.MALE, new HashSet<>(Set.of(new Car(1L))), false);
  public static final Driver TEST_DRIVER_E2E =
            new Driver(TEST_DRIVER_ID, "Иван", "Иванов", "ivan.ivanov@example.com", "89001234567", Gender.MALE, new HashSet<>(Set.of(new Car(6L), new Car(7L), new Car(1L))), false);
    public static final Driver TEST_DRIVER_WITHOUT_CAR =
            new Driver(TEST_DRIVER_ID, "ILYA", "SHESTAKOV", "test@example.com", "+375295035659", Gender.MALE, new HashSet<>(), false);
    public static final Passenger TEST_PASSENGER =
            new Passenger(TEST_PASSENGER_ID, "NIKITA", "KOMKOV", "secondTest@example.com", "+375445986558");
    public static final Passenger TEST_PASSENGER_E2E =
            new Passenger(TEST_PASSENGER_ID, "John", "Doe", "john.doe@example.com", "+375291234567");
    public static final RoutingResponse TEST_ROUTING_RESPONSE =
            new RoutingResponse(List.of(new PathDto(TEST_DISTANCE, TEST_DURING)));
    public static final DriverResponse TEST_DRIVER_RESPONSE =
            new DriverResponse(TEST_DRIVER_ID, "ILYA", "SHESTAKOV", "test@example.com", "+375295035659", Gender.MALE, new HashSet<>(Set.of(1L)), false);
    public static final DriverResponse TEST_DRIVER_RESPONSE_WITHOUT_CAR =
            new DriverResponse(TEST_DRIVER_ID, "ILYA", "SHESTAKOV", "test@example.com", "+375295035659", Gender.MALE, new HashSet<>(), false);
    public static final PassengerResponse TEST_PASSENGER_RESPONSE =
            new PassengerResponse(TEST_PASSENGER_ID, "NIKITA", "KOMKOV", "secondTest@example.com", "+375445986558");

    public static RideRequest defaultRideRequest() {
        return new RideRequest(TEST_DRIVER_ID, TEST_PASSENGER_ID,
                TEST_PICKUP_ADDRESS, TEST_DESTINATION_ADDRESS, TEST_STATUS);
    }

    public static RideRequest defaultRideRequestWithoutCar() {
        return new RideRequest(6L, TEST_PASSENGER_ID,
                TEST_PICKUP_ADDRESS, TEST_DESTINATION_ADDRESS, TEST_STATUS);
    }

    public static RideRequest defaultRideDriverNotFoundRequest() {
        return new RideRequest(999L, TEST_PASSENGER_ID,
                TEST_PICKUP_ADDRESS, TEST_DESTINATION_ADDRESS, TEST_STATUS);
    }

    public static RideRequest defaultRidePassengerNotFoundRequest() {
        return new RideRequest(TEST_DRIVER_ID, 999L,
                TEST_PICKUP_ADDRESS, TEST_DESTINATION_ADDRESS, TEST_STATUS);
    }

    public static RideResponse defaultRideResponse() {
        return new RideResponse(TEST_ID, TEST_DRIVER, TEST_PASSENGER, TEST_PICKUP_ADDRESS, TEST_DESTINATION_ADDRESS, TEST_STATUS,
                TEST_DISTANCE, TEST_TIME, TEST_DURING, TEST_PRICE);
    }

    public static Ride defaultRide() {
        return new Ride(TEST_ID, TEST_DRIVER, TEST_PASSENGER, TEST_PICKUP_ADDRESS, TEST_DESTINATION_ADDRESS, TEST_STATUS,
                TEST_DISTANCE, TEST_TIME, TEST_DURING, TEST_PRICE);
    }

    public static RideStatusRequest defaultRideStatusRequest() {
        return new RideStatusRequest(Status.ACCEPTED);
    }

    public static RideUpdateRequest defaultRideUpdateRequest() {
        return new RideUpdateRequest(TEST_PICKUP_ADDRESS, TEST_DESTINATION_ADDRESS);
    }

    public static RideResponse updatedRideResponse() {
        return new RideResponse(TEST_ID, TEST_DRIVER, TEST_PASSENGER, TEST_PICKUP_ADDRESS_FOR_UPDATE, TEST_DESTINATION_ADDRESS_FOR_UPDATE, TEST_STATUS,
                TEST_DISTANCE, TEST_TIME, TEST_DURING, TEST_PRICE);
    }

    public static RideResponse updatedRideResponseE2E() {
        return new RideResponse("67ce92e97960c17aa8048ab5", TEST_DRIVER_E2E, TEST_PASSENGER_E2E, "55.48632,28.765492", "55.521048,28.663522", Status.ACCEPTED,
                TEST_DISTANCE, TEST_TIME, TEST_DURING, TEST_PRICE);
    }

    public static RideRequest invalidRequest() {
        return RideRequest.builder()
                .build();
    }
}
