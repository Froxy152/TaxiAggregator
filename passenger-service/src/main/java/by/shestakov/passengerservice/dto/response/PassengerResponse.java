package by.shestakov.passengerservice.dto.response;

import jakarta.validation.Valid;
import lombok.Builder;

@Builder
@Valid
public record PassengerResponse(
        Long id,
        String name,
        String lastName,
        String email,
        String phoneNumber,
        Boolean isDeleted

){
}
