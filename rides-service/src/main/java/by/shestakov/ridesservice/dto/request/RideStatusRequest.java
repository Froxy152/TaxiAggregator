package by.shestakov.ridesservice.dto.request;

import by.shestakov.ridesservice.entity.Status;

public record RideStatusRequest(
        Status status
) {
}
