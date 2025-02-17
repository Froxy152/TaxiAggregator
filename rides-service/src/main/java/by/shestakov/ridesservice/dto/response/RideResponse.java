package by.shestakov.ridesservice.dto.response;

import by.shestakov.ridesservice.entity.Passenger;
import by.shestakov.ridesservice.entity.Status;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public record RideResponse(
        Long driver,

        Passenger passenger,

        String pickUpAddress,

        String destinationAddress,

        Status status,

        Double distance,

        LocalDateTime time,

        Integer duringRide,

        BigDecimal price
) {
}
