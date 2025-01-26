package by.shestakov.driverservice.mapper;

import by.shestakov.driverservice.dto.request.CarDtoRequest;
import by.shestakov.driverservice.dto.response.CarDtoResponse;
import by.shestakov.driverservice.entity.Car;

public interface CarMapper {

    public CarDtoResponse toDto(Car car);

    public Car toEntity(CarDtoRequest carDtoRequest);

    public void updateToExists(CarDtoRequest carDtoRequest, Car car);
}
