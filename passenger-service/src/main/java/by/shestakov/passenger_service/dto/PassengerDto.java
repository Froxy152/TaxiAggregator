package by.shestakov.passenger_service.dto;

import by.shestakov.passenger_service.util.ValidationConstants;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Valid
public class PassengerDto {

    @NotNull(message = ValidationConstants.MANDATORY_NAME_FIELD)
    @NotBlank(message = ValidationConstants.MANDATORY_NAME_FIELD)
    private String name;

    @NotNull(message = ValidationConstants.MANDATORY_SECOND_NAME_FIELD)
    @NotBlank(message = ValidationConstants.MANDATORY_SECOND_NAME_FIELD)
    private String secondName;

    @NotNull(message = ValidationConstants.MANDATORY_EMAIL_FIELD)
    @Email(regexp = ValidationConstants.EMAIL_REGEXP, message = ValidationConstants.MANDATORY_EMAIL_FIELD)
    @NotBlank(message = ValidationConstants.MANDATORY_EMAIL_FIELD)
    private String email;


    @NotBlank(message = ValidationConstants.MANDATORY_PHONE_FIELD)
    @NotNull(message = ValidationConstants.MANDATORY_PHONE_FIELD)
    @Pattern(regexp = ValidationConstants.PHONE_NUMBER_REGEXP)
    private String phoneNumber;


}
