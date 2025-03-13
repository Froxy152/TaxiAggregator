package by.shestakov.ratingservice.dto.response;

import java.math.BigDecimal;

public record AverageRatingResponse(
        BigDecimal average
) {
}
