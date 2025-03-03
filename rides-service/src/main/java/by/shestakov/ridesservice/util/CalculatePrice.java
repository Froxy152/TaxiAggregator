package by.shestakov.ridesservice.util;

import by.shestakov.ridesservice.util.constant.PriceConstant;
import java.math.BigDecimal;
import java.math.RoundingMode;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class CalculatePrice {
    public static BigDecimal mathPrice(Double distance, Integer duringTime) {
        return BigDecimal.valueOf(PriceConstant.START_PRICE + ((duringTime * PriceConstant.TIME_RATE_PER_MINUTE)
                + (distance * PriceConstant.DISTANCE_RATE_PER_KM))).setScale(2, RoundingMode.HALF_UP);
    }

    public static Integer msToMin(Integer timeInMs) {
        return timeInMs / 60000;
    }

    public static Double meterToKilometers(Double distance) {
        return distance / 1000;
    }
}
