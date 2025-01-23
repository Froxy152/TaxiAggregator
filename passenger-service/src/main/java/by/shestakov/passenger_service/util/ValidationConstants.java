package by.shestakov.passenger_service.util;

public class ValidationConstants {
    public static final String EMAIL_REGEXP = "^([\\w-\\.]+){1,64}@([\\w&&[^_]]+){2,255}.[a-z]{2,}$";
    public static final String PHONE_NUMBER_REGEXP = "^\\+375(17|25|29|33|44)\\d{7}$";
    public static final String MANDATORY_PHONE_FIELD = "Phone number is mandatory";
    public static final String MANDATORY_NAME_FIELD = "Name is mandatory";
    public static final String MANDATORY_SECOND_NAME_FIELD = "Second name is mandatory";
    public static final String MANDATORY_EMAIL_FIELD = "Email is mandatory";
}
