package by.shestakov.driverservice.service;

import by.shestakov.driverservice.dto.request.CarDtoRequest;
import by.shestakov.driverservice.dto.response.CarDtoResponse;

public interface CarService {
    public CarDtoResponse createCar(CarDtoRequest carDtoRequest, Long id);

    public CarDtoResponse updateCar(CarDtoRequest carDtoRequest, Long id);

    public void deleteCar(Long id);
}
