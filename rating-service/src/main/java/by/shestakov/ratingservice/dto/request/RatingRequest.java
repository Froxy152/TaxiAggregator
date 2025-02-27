package by.shestakov.ratingservice.dto.request;

import by.shestakov.ratingservice.entity.RatedBy;
import by.shestakov.ratingservice.util.ValidationConstants;
import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
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

    @NotNull(message = ValidationConstants.RATE_IS_MANDATORY)
    @DecimalMin(value = "0.0", message = ValidationConstants.RATE_CANNOT_BE_LOWER, inclusive = true)
    @DecimalMax(value = "5.0", message = ValidationConstants.RATE_CANNOT_BE_HIGHER, inclusive = true)
    Double rate,

    String commentary,

    @NotNull(message = ValidationConstants.RATED_BY_IS_MANDATORY)
    RatedBy ratedBy
) {
}
