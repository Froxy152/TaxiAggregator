package by.shestakov.ratingservice.dto.response;


import by.shestakov.ratingservice.entity.RatedBy;
import java.math.BigDecimal;

public record RatingResponse(
        String id,
        String rideId,
        Long passengerId,
        Long driverId,
        BigDecimal rate,
        String commentary,
        RatedBy ratedBy
) {
    @Override
    public String toString() {
        return "RatingResponse{" +
                "id='" + id + '\'' +
                ", rideId='" + rideId + '\'' +
                ", passengerId=" + passengerId +
                ", driverId=" + driverId +
                ", rate=" + rate +
                ", commentary='" + commentary + '\'' +
                ", ratedBy=" + ratedBy +
                '}';
    }
}
