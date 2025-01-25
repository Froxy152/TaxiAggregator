package by.shestakov.driverservice.service;

import by.shestakov.driverservice.dto.CarDto;

public interface CarService {
    public CarDto createCar(CarDto carDto, Long id);

    public CarDto updateCar(CarDto carDto, Long id);

    public CarDto deleteCar(Long id);
}
