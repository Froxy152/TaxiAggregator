package by.shestakov.driverservice.service;

import by.shestakov.driverservice.dto.request.CarRequest;
import by.shestakov.driverservice.dto.response.CarResponse;

import java.util.List;

public interface CarService {
    public List<CarResponse> getAllCars();

    public CarResponse createCar(CarRequest carRequest, Long id);

    public CarResponse updateCar(CarRequest carRequest, Long id);

    public void deleteCar(Long id);
}
