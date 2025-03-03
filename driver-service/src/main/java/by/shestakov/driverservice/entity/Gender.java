package by.shestakov.driverservice.entity;

import lombok.Getter;

@Getter
public enum Gender {
    MALE(1),
    FEMALE(2),
    OTHER(3);

    private final int code;

    Gender(Integer code) {
        this.code = code;
    }

    public static Gender fromValue(int value) {
        for (Gender gender : Gender.values()) {
            if (gender.getCode() == value) {
                return gender;
            }
        }
        throw new IllegalArgumentException("Unknown value: " + value);
    }

}
