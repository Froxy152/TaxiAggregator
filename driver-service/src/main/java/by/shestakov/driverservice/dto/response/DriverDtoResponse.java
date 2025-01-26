package by.shestakov.driverservice.dto.response;

import by.shestakov.driverservice.entity.Car;
import by.shestakov.driverservice.entity.Gender;

import java.util.List;

public record DriverDtoResponse(
        Long id,
        String name,
        String lastName,
        String email,
        String phoneNumber,
        Gender gender,
        List<Car> cars
) {
}
