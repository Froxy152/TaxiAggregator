package by.shestakov.ratingservice.dto;

public record RatingRequest(
        String rideId,
        Long passengerId,
        Long driverId,
        Integer mark,
        String commentary
) {
}
