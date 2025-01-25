package by.shestakov.driverservice.dto;


import by.shestakov.driverservice.entity.Driver;

public record CarDto(
        String mark,

        String number,

        String color

) {
    @Override
    public String toString() {
        return "CarDto{" +
                "mark='" + mark + '\'' +
                ", number='" + number + '\'' +
                ", color='" + color + '\'' +
                '}';
    }
}
