package by.shestakov.driverservice.mapper;

import by.shestakov.driverservice.dto.CarDto;
import by.shestakov.driverservice.entity.Car;

public interface CarMapper {

    public CarDto toDto(Car car);

    public Car toEntity(CarDto carDto);

    public void updateToExists(CarDto carDto, Car car);
}
