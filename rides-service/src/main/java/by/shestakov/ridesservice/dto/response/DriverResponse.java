package by.shestakov.ridesservice.dto.response;

import by.shestakov.ridesservice.entity.Gender;
import java.util.List;
import java.util.Set;

public record DriverResponse(
    Long id,
    String name,
    String lastName,
    String email,
    String phoneNumber,
    Gender gender,
    Set<Long> cars,
    Boolean isDeleted
) {
}
