package by.shestakov.driverservice.dto.response;

import by.shestakov.driverservice.entity.Gender;
import java.math.BigDecimal;
import java.util.Set;
import lombok.Builder;

@Builder
public record DriverResponse(
    Long id,
    String name,
    String lastName,
    String email,
    String phoneNumber,
    Gender gender,
    BigDecimal rating,
    Boolean isDeleted,
    Set<Long> cars
) {
}
