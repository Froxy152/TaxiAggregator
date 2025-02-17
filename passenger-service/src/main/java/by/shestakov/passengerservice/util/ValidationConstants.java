package by.shestakov.passengerservice.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ValidationConstants {
    public static final String PHONE_NUMBER_REGEXP = "^\\+375(17|25|29|33|44)\\d{7}$";
    public static final String MANDATORY_PHONE_FIELD = "Phone number is mandatory";
    public static final String MANDATORY_NAME_FIELD = "Name is mandatory";
    public static final String MANDATORY_SECOND_NAME_FIELD = "Second name is mandatory";
    public static final String MANDATORY_EMAIL_FIELD = "Email is mandatory";
}
