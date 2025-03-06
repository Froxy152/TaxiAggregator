package by.shestakov.constant;

import by.shestakov.driverservice.dto.request.DriverRequest;
import by.shestakov.driverservice.dto.request.DriverUpdateRequest;
import by.shestakov.driverservice.dto.response.DriverResponse;
import by.shestakov.driverservice.entity.Car;
import by.shestakov.driverservice.entity.Driver;
import by.shestakov.driverservice.entity.Gender;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

public final class TestDriverData {

    public static final Long TEST_ID = 1L;
    public static final String TEST_NAME = "Ilya";
    public static final String TEST_LASTNAME = "Shestakov";
    public static final String TEST_EMAIL = "test@example.com";
    public static final String TEST_NUMBER = "+375299208505";
    public static final Gender TEST_GENDER = Gender.OTHER;
    public static final BigDecimal TEST_RATING = new BigDecimal("0.0");
    public static final Set<Long> TEST_CARS_DTO = new HashSet<>();
    public static final Set<Car> TEST_CARS_ENTITY = new HashSet<>();

    public static DriverRequest defaultDriverRequest() {
        return DriverRequest.builder()
            .name(TEST_NAME)
            .lastName(TEST_LASTNAME)
            .email(TEST_EMAIL)
            .phoneNumber(TEST_NUMBER)
            .gender(TEST_GENDER)
            .build();
    }

    public static DriverRequest alreadyEmailDriverRequest() {
        return DriverRequest.builder()
            .name(TEST_NAME)
            .lastName(TEST_LASTNAME)
            .email("ivan.ivanov@example.com")
            .phoneNumber(TEST_NUMBER)
            .gender(TEST_GENDER)
            .build();
    }

    public static DriverRequest alreadyNumberDriverRequest() {
        return DriverRequest.builder()
            .name(TEST_NAME)
            .lastName(TEST_LASTNAME)
            .email(TEST_EMAIL)
            .phoneNumber("+375295035305")
            .gender(TEST_GENDER)
            .build();
    }

    public static DriverResponse defaultDriverResponse() {
        return DriverResponse.builder()
            .id(TEST_ID)
            .name(TEST_NAME)
            .lastName(TEST_LASTNAME)
            .email(TEST_EMAIL)
            .phoneNumber(TEST_NUMBER)
            .gender(TEST_GENDER)
            .rating(TEST_RATING)
            .cars(TEST_CARS_DTO)
            .isDeleted(false)
            .build();
    }

    public static Driver defaultDriver() {
        return new Driver(TEST_ID, TEST_NAME, TEST_LASTNAME, TEST_EMAIL, TEST_NUMBER, TEST_GENDER, TEST_RATING,
            false, TEST_CARS_ENTITY);
    }

    public static DriverUpdateRequest driverUpdateRequest() {
        return new DriverUpdateRequest("Nikita", "Komkov", "test@exaple.com", "+375445986231",
            BigDecimal.valueOf(2.0), Gender.MALE);
    }

    public static DriverUpdateRequest alReadyDriverUpdateRequest() {
        return new DriverUpdateRequest("Nikita", "Komkov", "anna.petrova@example.com", "+375295035305",
            BigDecimal.valueOf(2.0), Gender.MALE);
    }

    public static DriverResponse updatedDriver() {
        return DriverResponse.builder()
            .id(TEST_ID)
            .name("Nikita")
            .lastName("Komkov")
            .email("test@example.com")
            .phoneNumber("+375445986231")
            .gender(Gender.MALE)
            .rating(BigDecimal.valueOf(2.0))
            .cars(TEST_CARS_DTO)
            .isDeleted(false)
            .build();
    }

    public static DriverRequest invalidDriverRequest() {
        return DriverRequest.builder()
            .build();
    }
}
