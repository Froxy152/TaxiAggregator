package by.shestakov.ratingservice.dto.response;

import java.math.BigDecimal;

public record RatingResponse(
        String id,
        String rideId,
        Long passengerId,
        Long driverId,
        BigDecimal mark,
        String commentary
) {
}
