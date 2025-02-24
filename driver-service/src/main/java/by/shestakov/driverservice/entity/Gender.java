package by.shestakov.driverservice.entity;

import lombok.Getter;

@Getter
public enum Gender {
    MALE(1),
    FEMALE(2),
    OTHER(3);

    private final Integer code;

    Gender(Integer code) {
        this.code = code;
    }

    public static Gender fromValue(String value) {
        for (Gender gender : Gender.values()) {
            if (gender.name().equals(value)) {
                return gender;
            }
        }
        throw new IllegalArgumentException("Unknown value: " + value);
    }

    public static Gender fromCode(int code) {
        for (Gender gender : Gender.values()) {
            if (gender.getCode().equals(code)) {
                return gender;
            }
        }
        throw new IllegalArgumentException("Unknown value: " + code);
    }

}
