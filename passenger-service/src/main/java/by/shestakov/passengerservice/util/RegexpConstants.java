package by.shestakov.passengerservice.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class RegexpConstants {
    public static final String PHONE_NUMBER_REGEXP = "^\\+375(17|25|29|33|44)\\d{7}$";
}