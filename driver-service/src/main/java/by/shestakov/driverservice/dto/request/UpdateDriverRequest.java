package by.shestakov.driverservice.dto.request;

import by.shestakov.driverservice.entity.Gender;
import by.shestakov.driverservice.util.GenderConverter;
import by.shestakov.driverservice.util.RegexpConstants;
import jakarta.persistence.Convert;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;

public record UpdateDriverRequest(
    String name,

    String lastName,

    @Email
    String email,

    @Pattern(regexp = RegexpConstants.PHONE_NUMBER_REGEXP)
    String phoneNumber,

    @Convert(converter = GenderConverter.class)
    Gender gender
) {
}
