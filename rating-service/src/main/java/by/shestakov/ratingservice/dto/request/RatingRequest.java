package by.shestakov.ratingservice.dto.request;

import by.shestakov.ratingservice.util.ValidationConstants;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Valid
public record RatingRequest(

        @NotBlank(message = ValidationConstants.RIDE_ID_IS_MANDATORY)
        String rideId,

        @NotNull(message = ValidationConstants.PASSENGER_ID_IS_MANDATORY)
        Long passengerId,

        @NotNull(message = ValidationConstants.DRIVER_ID_IS_MANDATORY)
        Long driverId,

        @NotNull(message = ValidationConstants.MARK_IS_MANDATORY)
        Integer mark,

        String commentary
) {
}
