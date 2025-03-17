package by.shestakov.driverservice.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;


public record UpdateRatingRequest(
    @JsonProperty("id")
    Long id,
    @JsonProperty("rating")
    BigDecimal rating
) {

}
