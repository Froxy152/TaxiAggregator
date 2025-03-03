package by.shestakov.ratingservice.dto.response;


import by.shestakov.ratingservice.entity.RatedBy;

public record RatingResponse(
        String id,
        String rideId,
        Long passengerId,
        Long driverId,
        Double rate,
        String commentary,
        RatedBy ratedBy
) {
}
