package by.shestakov.ridesservice.dto.response;

import by.shestakov.ridesservice.entity.Gender;

public record DriverResponse(
        Long id,
        String name,
        String lastName,
        String email,
        String phoneNumber,
        Gender gender,
        Long carId
        // todo add list, gender
) {
}
