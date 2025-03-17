package by.shestakov.constant;

import by.shestakov.driverservice.dto.request.UpdateRatingRequest;
import java.math.BigDecimal;

public final class TestKafkaData {
    public static UpdateRatingRequest ratingRequest() {
        return new UpdateRatingRequest(1L, BigDecimal.valueOf(5.0));
    }
}
