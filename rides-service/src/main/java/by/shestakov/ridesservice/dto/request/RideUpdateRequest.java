package by.shestakov.ridesservice.dto.request;

import by.shestakov.ridesservice.util.constant.ValidationConstant;
import jakarta.validation.constraints.NotBlank;

public record RideUpdateRequest(
        @NotBlank(message = ValidationConstant.PICKUP_ADDRESS_MANDATORY)
        String pickUpAddress,

        @NotBlank(message = ValidationConstant.DESTINATION_ADDRESS_MANDATORY)
        String destinationAddress
) {

}
