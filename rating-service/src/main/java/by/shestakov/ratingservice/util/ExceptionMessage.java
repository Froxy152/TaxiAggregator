package by.shestakov.ratingservice.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ExceptionMessage {
    public static final String ONLY_ONE_MESSAGE = "User can send 1 review";
    public static final String NOT_FOUND_MESSAGE = "Data not found";
    public static final String ILLEGAL_EXCEPTION_MESSAGE = "Invalid Enum value : %s";
}
