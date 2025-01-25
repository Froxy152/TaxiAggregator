package by.shestakov.passengerservice.dto.response;

import by.shestakov.passengerservice.util.ValidationConstants;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;


@Valid
public record PassengerDtoResponse(
        Long id,
        String name,
        String lastName,
        String email,
        String phoneNumber,
        Boolean isDeleted

){
}
