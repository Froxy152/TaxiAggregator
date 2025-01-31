package by.shestakov.driverservice.dto.request;


import by.shestakov.driverservice.util.RegexpConstants;
import by.shestakov.driverservice.util.ValidationConstants;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

@Valid
public record CarRequest(

        @NotBlank(message = ValidationConstants.MANDATORY_CAR_BRAND_FIELD)
        String carBrand,

        @Pattern(regexp = RegexpConstants.CAR_NUMBER_REGEXP)
        String carNumber,

        @NotBlank(message = ValidationConstants.MANDATORY_CAR_COLOR_FIELD)
        String carColor
) {

}
