package by.shestakov.ridesservice.dto.request;

import by.shestakov.ridesservice.entity.Status;
import by.shestakov.ridesservice.util.constant.ValidationConstant;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

@Valid
public record RideRequest(
        @NotBlank(message = ValidationConstant.DRIVER_ID_MANDATORY)
        Long driverId,

        @NotBlank(message = ValidationConstant.PASSENGER_ID_MANDATORY)
        Long passengerId,

        @NotBlank(message = ValidationConstant.PICKUP_ADDRESS_MANDATORY)
        String pickUpAddress,

        @NotBlank(message = ValidationConstant.DESTINATION_ADDRESS_MANDATORY)
        String destinationAddress,

        @NotBlank(message = ValidationConstant.STATUS_MANDATORY)
        Status status
) {
}
