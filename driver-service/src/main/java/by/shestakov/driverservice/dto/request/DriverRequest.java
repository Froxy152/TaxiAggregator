package by.shestakov.driverservice.dto.request;

import by.shestakov.driverservice.entity.Gender;
import by.shestakov.driverservice.util.RegexpConstants;
import by.shestakov.driverservice.util.ValidationConstants;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

@Valid
public record DriverRequest(
        @NotNull(message = ValidationConstants.MANDATORY_NAME_FIELD)
        @NotBlank(message = ValidationConstants.MANDATORY_NAME_FIELD)
        String name,

        @NotNull(message = ValidationConstants.MANDATORY_SECOND_NAME_FIELD)
        @NotBlank(message = ValidationConstants.MANDATORY_SECOND_NAME_FIELD)
        String lastName,

        @NotNull(message = ValidationConstants.MANDATORY_EMAIL_FIELD)
        @NotBlank(message = ValidationConstants.MANDATORY_EMAIL_FIELD)
        @Email
        String email,

        @NotNull(message = ValidationConstants.MANDATORY_PHONE_FIELD)
        @NotBlank(message = ValidationConstants.MANDATORY_PHONE_FIELD)
        @Pattern(regexp = RegexpConstants.PHONE_NUMBER_REGEXP)
        String phoneNumber,

        @NotNull(message = ValidationConstants.MANDATORY_GENDER_FIELD)
        @NotBlank(message = ValidationConstants.MANDATORY_GENDER_FIELD)
        Gender gender
) {
}
