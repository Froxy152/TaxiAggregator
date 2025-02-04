package by.shestakov.ridesservice.entity;

import lombok.Getter;

@Getter
public enum Status {
    CREATED(1),
    ACCEPTED(2),
    ON_THE_WAY_TO_PASSENGER(3),
    ON_THE_WAY_TO_DESTINATION(4),
    FINISHED(5),
    DECLINED(6);

    private final int code;

    Status(Integer code) {
        this.code = code;
    }

    public static Status fromValue(int value) {
        for (Status status : Status.values()) {
            if (status.getCode() == value) {
                return status;
            }
        }
        throw new IllegalArgumentException("Invalid status code: " + value);
    }
}
