package by.shestakov.ridesservice.dto.request;

import by.shestakov.ridesservice.entity.Status;
import by.shestakov.ridesservice.util.constant.ValidationConstant;
import jakarta.validation.constraints.NotBlank;

public record RideStatusRequest(
        @NotBlank(message = ValidationConstant.STATUS_MANDATORY)
        Status status
) {
}
