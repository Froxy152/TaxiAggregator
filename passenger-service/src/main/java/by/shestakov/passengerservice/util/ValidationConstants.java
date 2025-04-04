package by.shestakov.passengerservice.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ValidationConstants {
    public static final String MANDATORY_PHONE_FIELD = "Phone number is mandatory";
    public static final String MANDATORY_NAME_FIELD = "Name is mandatory";
    public static final String MANDATORY_SECOND_NAME_FIELD = "Second name is mandatory";
    public static final String MANDATORY_EMAIL_FIELD = "Email is mandatory";
}
