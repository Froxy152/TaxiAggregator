package by.shestakov.driverservice.util;

public final class RegexpConstants {
    public static final String PHONE_NUMBER_REGEXP = "^\\+375(17|25|29|33|44)\\d{7}$";
    public static final String CAR_NUMBER_REGEXP = "^[АВЕКМНОРСТУХ]{1}[0-9]{3}[АВЕКМНОРСТУХ]{2}[0-9]{2,3}$";

    private RegexpConstants() {
    }
}
