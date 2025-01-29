package by.shestakov.driverservice.dto.request;


import by.shestakov.driverservice.util.ValidationConstants;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

@Valid
public record CarRequest(

        @NotNull(message = ValidationConstants.MANDATORY_CAR_BRAND_FIELD)
        @NotBlank (message = ValidationConstants.MANDATORY_CAR_BRAND_FIELD)
        String carBrand,

        @NotNull(message = ValidationConstants.MANDATORY_CAR_NUMBER_FIELD)
        @NotBlank(message = ValidationConstants.MANDATORY_CAR_NUMBER_FIELD)
        @Pattern(regexp = ValidationConstants.CAR_NUMBER_REGEXP)
        String carNumber,

        @NotNull(message = ValidationConstants.MANDATORY_CAR_COLOR_FIELD)
        @NotBlank(message = ValidationConstants.MANDATORY_CAR_COLOR_FIELD)
        String carColor
) {

}
