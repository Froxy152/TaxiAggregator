package by.shestakov.driverservice.dto.response;

import by.shestakov.driverservice.entity.Driver;

public record CarDtoResponse(

        Long id,
        String carBrand,
        String carNumber,
        String carColor,
        Long driverId,
        Boolean isDeleted
) {
}
