package by.shestakov.driverservice.service;

import by.shestakov.driverservice.dto.request.CarRequest;
import by.shestakov.driverservice.dto.response.CarResponse;
import by.shestakov.driverservice.dto.response.PageResponse;

public interface CarService {
    public PageResponse<CarResponse> getAllCars(Integer offset, Integer limit);

    public CarResponse createCar(CarRequest carRequest, Long id);

    public CarResponse updateCar(CarRequest carRequest, Long id);

    public void deleteCar(Long id);
}
