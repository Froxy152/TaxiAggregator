package by.shestakov.driverservice.dto.response;

import by.shestakov.driverservice.entity.Gender;
import java.util.Set;


public record DriverResponse(
    Long id,
    String name,
    String lastName,
    String email,
    String phoneNumber,
    Gender gender,
    Boolean isDeleted,
    Set<Long> cars
) {
}
