package by.shestakov.ratingservice.dto.request;

import java.math.BigDecimal;
import lombok.Builder;

@Builder
public record UpdateRatingRequest(
    Long id,
    BigDecimal rating
) {
}
