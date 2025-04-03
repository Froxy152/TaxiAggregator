package by.shestakov.constant;

import by.shestakov.driverservice.dto.request.CarRequest;
import by.shestakov.driverservice.dto.request.CarUpdateRequest;
import by.shestakov.driverservice.dto.response.CarResponse;
import by.shestakov.driverservice.entity.Car;
import by.shestakov.driverservice.entity.Driver;

public final class TestCarData {
    public static final Long TEST_CAR_ID = 1L;
    public static final Long INVALID_CAR_ID = 999L;
    public static final String TEST_CAR_BRAND = "BMW";
    public static final String TEST_CAR_BRAND_UPDATE = "AUDI";
    public static final String TEST_CAR_NUMBER = "А123ВС45";
    public static final String TEST_CAR_NUMBER_UPDATE = "А123ВС47";
    public static final String TEST_CAR_COLOR = "Black";
    public static final String TEST_CAR_COLOR_UPDATE = "GRAY";
    public static final Long TEST_DRIVER_ID = 1L;
    public static final Long INVALID_DRIVER_ID = 999L;
    public static final Driver TEST_DRIVER = new Driver();
    public static final String DEFAULT_CAR_ADDRESS = "/api/v1/cars";

    public static CarRequest defaultCarRequest() {
        return new CarRequest(TEST_CAR_BRAND, TEST_CAR_NUMBER, TEST_CAR_COLOR);
    }

    public static CarRequest alreadyCarRequest() {
        return new CarRequest(TEST_CAR_BRAND, "М012МТ178", TEST_CAR_COLOR);
    }

    public static CarResponse defaultCarResponse() {
        return new CarResponse(7L, TEST_CAR_BRAND, TEST_CAR_NUMBER, TEST_CAR_COLOR, 7L, false);
    }

    public static Car defaultCar() {
        return new Car(TEST_CAR_ID, TEST_CAR_BRAND, TEST_CAR_NUMBER, TEST_CAR_COLOR, false, TEST_DRIVER);
    }

    public static CarUpdateRequest updateRequest() {
        return new CarUpdateRequest(TEST_CAR_BRAND_UPDATE, TEST_CAR_NUMBER_UPDATE, TEST_CAR_COLOR_UPDATE);
    }

    public static CarUpdateRequest alreadyUpdateRequest() {
        return new CarUpdateRequest(TEST_CAR_BRAND_UPDATE, "М012МТ178", TEST_CAR_COLOR_UPDATE);
    }

    public static CarResponse updatedResponse() {
        return new CarResponse(TEST_CAR_ID, TEST_CAR_BRAND_UPDATE, TEST_CAR_NUMBER_UPDATE, TEST_CAR_COLOR_UPDATE,
            TEST_DRIVER_ID, false);
    }

    public static CarRequest invalidRequest() {
        return new CarRequest("", "", "");
    }
}
