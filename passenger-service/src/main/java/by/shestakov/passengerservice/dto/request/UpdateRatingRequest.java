package by.shestakov.passengerservice.dto.request;

import java.math.BigDecimal;

public record UpdateRatingRequest(
    Long id,
    BigDecimal rating
) {
}
