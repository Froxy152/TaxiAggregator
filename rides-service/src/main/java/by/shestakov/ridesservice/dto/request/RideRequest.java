package by.shestakov.ridesservice.dto.request;

import by.shestakov.ridesservice.entity.Status;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

@Valid
public record RideRequest(
        @NotBlank
        Long driverId,
        @NotBlank
        Long passengerId,
        @NotBlank
        String addressFrom,
        @NotBlank
        String addressDestination,
        @NotBlank
        Status status
) {
}
