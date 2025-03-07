package by.shestakov.ridesservice.dto.response;

import by.shestakov.ridesservice.entity.Gender;
import java.io.Serializable;
import java.util.List;
import java.util.Set;
import lombok.AllArgsConstructor;


public record DriverResponse(
    Long id,
    String name,
    String lastName,
    String email,
    String phoneNumber,
    Gender gender,
    Set<Long> cars,
    Boolean isDeleted
) implements Serializable {
}
