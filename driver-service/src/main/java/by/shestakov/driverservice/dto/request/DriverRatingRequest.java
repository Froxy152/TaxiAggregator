package by.shestakov.driverservice.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;



public record DriverRatingRequest(
    @JsonProperty("id")
    Long id,
    @JsonProperty("rating")
    Double rating
) {

}
