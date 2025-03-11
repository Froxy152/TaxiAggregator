package by.shestakov.ratingservice.constant;

import by.shestakov.ratingservice.dto.request.CommentaryDto;
import by.shestakov.ratingservice.dto.request.RatingRequest;
import by.shestakov.ratingservice.dto.response.AverageRatingResponse;
import by.shestakov.ratingservice.dto.response.RatingResponse;
import by.shestakov.ratingservice.entity.RatedBy;
import by.shestakov.ratingservice.entity.Rating;
import java.math.BigDecimal;

public final class TestConstant {

    public static final String TEST_ID = "TEST_ID";
    public static final String TEST_INVALID_ID = "ASDASDAS";
    public static final String TEST_RIDE_ID = "ID";
    public static final Long TEST_DRIVER_ID = 1L;
    public static final Long TEST_DRIVER_INVALID_ID = 999L;
    public static final Long TEST_PASSENGER_ID = 1L;
    public static final Double TEST_RATE = 0.0;
    public static final String TEST_COMMENTARY = "test";
    public static final RatedBy TEST_RATED_BY_DRIVER = RatedBy.DRIVER;
    public static final RatedBy TEST_RATED_BY_PASSENGER = RatedBy.PASSENGER;
    public static final CommentaryDto TEST_COMMENTARY_DTO = new CommentaryDto(TEST_COMMENTARY);

    public static RatingRequest defaultRatingDriverRequest() {
        return new RatingRequest(TEST_RIDE_ID, TEST_DRIVER_ID, TEST_PASSENGER_ID, TEST_RATE, TEST_COMMENTARY, TEST_RATED_BY_DRIVER);
    }

    public static RatingRequest defaultRatingPassengerRequest() {
        return new RatingRequest(TEST_RIDE_ID, TEST_DRIVER_ID, TEST_PASSENGER_ID, TEST_RATE, TEST_COMMENTARY, TEST_RATED_BY_PASSENGER);
    }

    public static RatingResponse defaultRatingDriverResponse() {
        return new RatingResponse(TEST_ID, TEST_RIDE_ID, TEST_PASSENGER_ID, TEST_DRIVER_ID, TEST_RATE, TEST_COMMENTARY, TEST_RATED_BY_DRIVER);
    }

    public static RatingResponse defaultRatingPassengerResponse() {
        return new RatingResponse(TEST_ID, TEST_RIDE_ID, TEST_PASSENGER_ID, TEST_DRIVER_ID, TEST_RATE, TEST_COMMENTARY, TEST_RATED_BY_PASSENGER);
    }

    public static Rating defaultRatingByDriver() {
        return new Rating(TEST_ID, TEST_RIDE_ID, TEST_PASSENGER_ID, TEST_DRIVER_ID, TEST_RATE, TEST_COMMENTARY, TEST_RATED_BY_DRIVER);
    }

    public static Rating defaultRatingByPassenger() {
        return new Rating(TEST_ID, TEST_RIDE_ID, TEST_PASSENGER_ID, TEST_DRIVER_ID, TEST_RATE, TEST_COMMENTARY, TEST_RATED_BY_PASSENGER);
    }

    public static Rating defaultRatingByPassengerForInsert() {
        return new Rating(TEST_ID, "NEW_RIDE", TEST_PASSENGER_ID, TEST_DRIVER_ID, TEST_RATE, TEST_COMMENTARY, TEST_RATED_BY_PASSENGER);
    }

    public static AverageRatingResponse averageRatingResponse() {
        return new AverageRatingResponse(BigDecimal.valueOf(2.0));
    }
}
