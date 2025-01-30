package by.shestakov.driverservice.dto.response;


public record CarResponse(
        Long id,
        String carBrand,
        String carNumber,
        String carColor,
        Long driver,
        Boolean isDeleted
) {
}
