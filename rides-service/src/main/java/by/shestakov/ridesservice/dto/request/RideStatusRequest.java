package by.shestakov.ridesservice.dto.request;

import by.shestakov.ridesservice.entity.Status;
import by.shestakov.ridesservice.util.constant.ValidationConstant;
import jakarta.validation.constraints.NotNull;

public record RideStatusRequest(
        @NotNull(message = ValidationConstant.STATUS_MANDATORY)
        Status status
) {
    @Override
    public String toString() {
        return "RideStatusRequest{" +
                "status=" + status +
                '}';
    }
}
