package by.shestakov.ratingservice.entity;

import by.shestakov.ratingservice.util.ExceptionMessage;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;


public enum RatedBy {
    DRIVER("DRIVER"),
    PASSENGER("PASSENGER");

    private final String value;

    RatedBy(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }

    @JsonCreator
    public static RatedBy fromValue(String value) {
        for (RatedBy rated : values()) {
            if (rated.value.equalsIgnoreCase(value)) {
                return rated;
            }
        }
        throw new IllegalArgumentException(ExceptionMessage.ILLEGAL_EXCEPTION_MESSAGE.formatted(value));
    }
}
