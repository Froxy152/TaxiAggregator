package by.shestakov.driverservice.dto.request;


import by.shestakov.driverservice.util.GenderConverter;
import by.shestakov.driverservice.util.RegexpConstants;
import by.shestakov.driverservice.util.ValidationConstants;
import jakarta.persistence.Convert;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

@Valid
public record DriverRequest(
        @NotBlank(message = ValidationConstants.MANDATORY_NAME_FIELD)
        String name,

        @NotBlank(message = ValidationConstants.MANDATORY_SECOND_NAME_FIELD)
        String lastName,

        @NotBlank(message = ValidationConstants.MANDATORY_EMAIL_FIELD)
        @Email
        String email,

        @NotBlank(message = ValidationConstants.MANDATORY_PHONE_FIELD)
        @Pattern(regexp = RegexpConstants.PHONE_NUMBER_REGEXP)
        String phoneNumber,

        @Convert(converter = GenderConverter.class)
        @NotNull(message = ValidationConstants.MANDATORY_GENDER_FIELD)
        Integer gender
) {
}
