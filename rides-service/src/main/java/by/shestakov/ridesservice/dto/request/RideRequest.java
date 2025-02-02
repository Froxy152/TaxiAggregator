package by.shestakov.ridesservice.dto.request;

import by.shestakov.ridesservice.entity.Status;

public record RideRequest(
        Long driverId,
        Long passengerId,
        String addressFrom,
        String addressDestination,
        Status status
) {
}
