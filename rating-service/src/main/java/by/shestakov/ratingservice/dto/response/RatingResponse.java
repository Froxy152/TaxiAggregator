package by.shestakov.ratingservice.dto.response;

import by.shestakov.ratingservice.entity.Driver;
import by.shestakov.ratingservice.entity.Passenger;

public record RatingResponse(
        String id,

        String rideId,

        Passenger passenger,

        Driver driver,

        Integer mark,

        String commentary
) {
}
