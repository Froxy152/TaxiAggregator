package by.shestakov.ridesservice.dto.response;

import by.shestakov.ridesservice.entity.Status;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record RideResponse(
        Long driverId,
        Long passengerId,
        String addressFrom,
        String addressDestination,
        Status status,
        Double distance,
        LocalDateTime time,
        Integer duringRide,
        BigDecimal price
) {
}
