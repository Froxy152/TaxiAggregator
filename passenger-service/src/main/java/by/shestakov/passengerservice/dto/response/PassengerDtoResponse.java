package by.shestakov.passengerservice.dto.response;

import by.shestakov.passengerservice.util.ValidationConstants;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;


@Valid
public record PassengerDtoResponse(

    @NotNull(message = ValidationConstants.MANDATORY_NAME_FIELD)
    @NotBlank(message = ValidationConstants.MANDATORY_NAME_FIELD)
     String name,

    @NotNull(message = ValidationConstants.MANDATORY_SECOND_NAME_FIELD)
    @NotBlank(message = ValidationConstants.MANDATORY_SECOND_NAME_FIELD)
    String lastName,

    @NotNull(message = ValidationConstants.MANDATORY_EMAIL_FIELD)
    @NotBlank(message = ValidationConstants.MANDATORY_EMAIL_FIELD)
    String email,


    @NotBlank(message = ValidationConstants.MANDATORY_PHONE_FIELD)
    @NotNull(message = ValidationConstants.MANDATORY_PHONE_FIELD)
    @Pattern(regexp = ValidationConstants.PHONE_NUMBER_REGEXP)
    String phoneNumber

){
}
