package by.shestakov.driverservice.dto.request;

import by.shestakov.driverservice.entity.Gender;

public record DriverDtoRequest(

        String name,

        String lastName,

        String email,

        Gender gender

) {
    @Override
    public String toString() {
        return "DriverDto{" +
                "name='" + name + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", gender=" + gender +
                '}';
    }
}
