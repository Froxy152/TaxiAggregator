package by.shestakov.ridesservice.util.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class CalculatePrice {
    public static Double mathPrice(Double distance, Integer duringTime){
        return (PriceConstant.START_PRICE + (duringTime * PriceConstant.TIME_RATE_PER_MINUTE) + (distance * PriceConstant.DISTANCE_RATE_PER_KM));
    }

    public static Integer msToMin(Integer timeInMs){
        return timeInMs / 60000;
    }

    public static Double meterToKilometers(Double distance){
        return distance / 1000;
    }
}
