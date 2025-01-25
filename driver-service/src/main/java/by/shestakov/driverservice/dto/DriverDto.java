package by.shestakov.driverservice.dto;

import by.shestakov.driverservice.entity.Car;
import by.shestakov.driverservice.entity.Gender;

import java.util.List;

public record DriverDto(

        String name,

        String lastName,

        String email,

        Gender gender,

        List<Car> cars

) {
}
