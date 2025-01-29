package by.shestakov.driverservice.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ValidationConstants {
    public static final String PHONE_NUMBER_REGEXP = "^\\+375(17|25|29|33|44)\\d{7}$";
    public static final String CAR_NUMBER_REGEXP = "^[АВЕКМНОРСТУХ]{1}[0-9]{3}[АВЕКМНОРСТУХ]{2}[0-9]{2,3}$";
    public static final String MANDATORY_PHONE_FIELD = "Phone number is mandatory";
    public static final String MANDATORY_NAME_FIELD = "Name is mandatory";
    public static final String MANDATORY_SECOND_NAME_FIELD = "Second name is mandatory";
    public static final String MANDATORY_EMAIL_FIELD = "Email is mandatory";
    public static final String MANDATORY_GENDER_FIELD = "Gender is mandatory";
    public static final String MANDATORY_CAR_COLOR_FIELD = "Car color is mandatory";
    public static final String MANDATORY_CAR_BRAND_FIELD = "Car brand is mandatory";
    public static final String MANDATORY_CAR_NUMBER_FIELD = "Car number is mandatory";
}
