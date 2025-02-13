package by.shestakov.ratingservice.dto.response;

import by.shestakov.ratingservice.entity.enums.Gender;
import java.util.List;


public record DriverResponse(
        Long id,

        String name,

        String lastName,

        String email,

        String phoneNumber,

        Gender gender,

        List<CarResponse> cars,

        Boolean isDeleted

) {
}
