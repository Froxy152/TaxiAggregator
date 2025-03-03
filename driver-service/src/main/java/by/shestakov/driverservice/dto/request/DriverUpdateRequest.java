package by.shestakov.driverservice.dto.request;

import by.shestakov.driverservice.entity.Gender;
import by.shestakov.driverservice.util.RegexpConstants;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;

public record DriverUpdateRequest(

        String name,

        String lastName,

        @Email
        String email,

        @Pattern(regexp = RegexpConstants.PHONE_NUMBER_REGEXP)
        String phoneNumber,

        Double rating,

        Gender gender
) {

}
