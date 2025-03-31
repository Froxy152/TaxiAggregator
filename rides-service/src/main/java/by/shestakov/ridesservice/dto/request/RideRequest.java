package by.shestakov.ridesservice.dto.request;

import by.shestakov.ridesservice.entity.Status;
import by.shestakov.ridesservice.util.constant.ValidationConstant;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
@Valid
public record RideRequest(
        @NotNull(message = ValidationConstant.DRIVER_ID_MANDATORY)
        Long driverId,

        @NotNull(message = ValidationConstant.PASSENGER_ID_MANDATORY)
        Long passengerId,

        @NotBlank(message = ValidationConstant.PICKUP_ADDRESS_MANDATORY)
        String pickUpAddress,

        @NotBlank(message = ValidationConstant.DESTINATION_ADDRESS_MANDATORY)
        String destinationAddress,

        @NotNull(message = ValidationConstant.STATUS_MANDATORY)
        Status status
) {
    @Override
    public String toString() {
        return "RideRequest{" +
                "driverId=" + driverId +
                ", passengerId=" + passengerId +
                ", pickUpAddress='" + pickUpAddress + '\'' +
                ", destinationAddress='" + destinationAddress + '\'' +
                ", status=" + status +
                '}';
    }
}
