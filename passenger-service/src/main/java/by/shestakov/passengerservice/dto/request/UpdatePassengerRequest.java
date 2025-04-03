package by.shestakov.passengerservice.dto.request;

import by.shestakov.passengerservice.util.RegexpConstants;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;

@Builder
public record UpdatePassengerRequest(

        String name,

        String lastName,

        @Email
        String email,

        @Pattern(regexp = RegexpConstants.PHONE_NUMBER_REGEXP)
        String phoneNumber
) {

}
