package by.shestakov.ridesservice.util.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ExceptionMessage {
    public static final String NOT_FOUND_MESSAGE = "Data with id: %d not found";
    public static final String ILLEGAL_EXCEPTION = "Invalid status code: %d";
}
