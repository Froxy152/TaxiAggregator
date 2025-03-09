package by.shestakov.passengerservice.constant;

import by.shestakov.passengerservice.dto.request.PassengerRequest;
import by.shestakov.passengerservice.dto.request.UpdatePassengerRequest;
import by.shestakov.passengerservice.dto.request.UpdateRatingRequest;
import by.shestakov.passengerservice.dto.response.PassengerResponse;
import by.shestakov.passengerservice.entity.Passenger;
import java.math.BigDecimal;
import java.math.RoundingMode;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UnitTestConstants {

    public static final Long TEST_ID = 1L;
    public static final Long TEST_INVALID_ID = 999L;
    public static final Long TEST_ALREADY_ID = 2L;
    public static final String TEST_NAME = "Ilya";
    public static final String TEST_LASTNAME = "Shestakov";
    public static final String TEST_NUMBER = "+375295896535";
    public static final String TEST_EMAIL = "test@example.com";
    public static final String TEST_EMAIL_FOR_UPDATE = "test@gmail.com";
    public static final BigDecimal TEST_RATING = new BigDecimal("0.0");

    public static PassengerResponse defaultResponse() {
        return PassengerResponse.builder()
                .id(TEST_ID)
                .name(TEST_NAME)
                .lastName(TEST_LASTNAME)
                .email(TEST_EMAIL)
                .phoneNumber(TEST_NUMBER)
                .rating(TEST_RATING)
                .isDeleted(false)
                .build();
    }

    public static PassengerResponse defaultFirstResponse() {
        return PassengerResponse.builder()
                .id(1L)
                .name("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .phoneNumber("+375291234567")
                .rating(BigDecimal.valueOf(4.5).setScale(2, RoundingMode.HALF_UP))
                .isDeleted(false)
                .build();
    }

    public static PassengerRequest defaultRequest() {
        return PassengerRequest.builder()
                .name(TEST_NAME)
                .lastName(TEST_LASTNAME)
                .email(TEST_EMAIL)
                .phoneNumber(TEST_NUMBER)
                .build();
    }

    public static PassengerRequest alreadyPassengerRequest() {

        return PassengerRequest.builder()
                .name(TEST_NAME)
                .lastName(TEST_LASTNAME)
                .email("john.doe@example.com")
                .phoneNumber("+375291234567")
                .build();
    }

    public static PassengerRequest invalidEmailRequest() {
        return PassengerRequest.builder()
                .name(TEST_NAME)
                .lastName(TEST_LASTNAME)
                .email("asdasdasdsa")
                .phoneNumber(TEST_NUMBER)
                .build();
    }

    public static PassengerRequest invalidPhoneNumberRequest() {
        return PassengerRequest.builder()
                .name(TEST_NAME)
                .lastName(TEST_LASTNAME)
                .email(TEST_EMAIL)
                .phoneNumber("5753775")
                .build();
    }

    public static UpdatePassengerRequest updatePassengerRequest() {
        return UpdatePassengerRequest.builder()
                .email(TEST_EMAIL_FOR_UPDATE)
                .build();
    }

    public static UpdatePassengerRequest updateAlreadyEmailPassengerRequest() {
        return UpdatePassengerRequest.builder()
                .email("frank.moore@example.com")
                .build();
    }

    public static UpdatePassengerRequest updateAlreadyNumberPassengerRequest() {
        return UpdatePassengerRequest.builder()
                .phoneNumber("+375291234567")
                .build();
    }

    public static PassengerResponse updatedPassengerResponse() {
        return PassengerResponse.builder()
                .id(3L)
                .name("Alice")
                .lastName("Johnson")
                .email(TEST_EMAIL_FOR_UPDATE)
                .phoneNumber("+375291234569")
                .isDeleted(false)
                .rating(BigDecimal.valueOf(4.2).setScale(2, RoundingMode.HALF_UP))
                .build();
    }

    public static Passenger defaultPassenger() {
        return new Passenger(TEST_ID, TEST_NAME, TEST_LASTNAME, TEST_EMAIL, TEST_NUMBER, TEST_RATING, false);
    }

    public static UpdateRatingRequest ratingRequest() {
        return new UpdateRatingRequest(TEST_ID, TEST_RATING);
    }
}
