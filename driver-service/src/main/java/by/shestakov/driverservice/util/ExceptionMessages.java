package by.shestakov.driverservice.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ExceptionMessages {
    public static final String CONFLICT_MESSAGE = "This %s already exists";
    public static final String NOT_FOUND_MESSAGE = "%s with id %d not found";
}
