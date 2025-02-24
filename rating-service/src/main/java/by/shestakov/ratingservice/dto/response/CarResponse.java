package by.shestakov.ratingservice.dto.response;

public record CarResponse(
        Long id,
        String carBrand,
        String carNumber,
        String carColor,
        Long driverId,
        Boolean isDeleted
) {
}
