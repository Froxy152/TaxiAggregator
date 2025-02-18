package by.shestakov.ridesservice.dto.response;

import by.shestakov.ridesservice.entity.Gender;
import java.util.List;

public record DriverResponse(
    Long id,
    String name,
    String lastName,
    String email,
    String phoneNumber,
    Gender gender,
    List<Long> carId,
    Boolean isDeleted
) {
}
