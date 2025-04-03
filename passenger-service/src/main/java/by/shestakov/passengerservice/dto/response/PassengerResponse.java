package by.shestakov.passengerservice.dto.response;

import jakarta.validation.Valid;
import java.math.BigDecimal;
import lombok.Builder;

@Builder
@Valid
public record PassengerResponse(
        Long id,

        String name,

        String lastName,

        String email,

        String phoneNumber,

        BigDecimal rating,

        Boolean isDeleted
) {
}