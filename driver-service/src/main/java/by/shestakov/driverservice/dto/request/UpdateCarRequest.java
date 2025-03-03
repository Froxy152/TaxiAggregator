package by.shestakov.driverservice.dto.request;

import by.shestakov.driverservice.util.RegexpConstants;
import jakarta.validation.constraints.Pattern;

public record UpdateCarRequest(
    String carBrand,

    @Pattern(regexp = RegexpConstants.CAR_NUMBER_REGEXP)
    String carNumber,

    String carColor
) {
}
