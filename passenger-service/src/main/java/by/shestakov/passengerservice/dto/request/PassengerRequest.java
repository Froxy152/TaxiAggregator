package by.shestakov.passengerservice.dto.request;

import by.shestakov.passengerservice.util.RegexpConstants;
import by.shestakov.passengerservice.util.ValidationConstants;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;


@Builder
@Valid
public record PassengerRequest(
        @NotBlank(message = ValidationConstants.MANDATORY_NAME_FIELD)
        String name,

        @NotBlank(message = ValidationConstants.MANDATORY_SECOND_NAME_FIELD)
        String lastName,

        @NotBlank(message = ValidationConstants.MANDATORY_EMAIL_FIELD)
        String email,

        @NotBlank(message = ValidationConstants.MANDATORY_PHONE_FIELD)
        @Pattern(regexp = RegexpConstants.PHONE_NUMBER_REGEXP)
        String phoneNumber

) {
}