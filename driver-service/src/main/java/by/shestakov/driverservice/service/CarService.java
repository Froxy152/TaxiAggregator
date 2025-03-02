package by.shestakov.driverservice.service;

import by.shestakov.driverservice.dto.request.CarRequest;
import by.shestakov.driverservice.dto.request.UpdateCarRequest;
import by.shestakov.driverservice.dto.response.CarResponse;
import by.shestakov.driverservice.dto.response.PageResponse;

public interface CarService {
    PageResponse<CarResponse> getAllCars(Integer offset, Integer limit);

    CarResponse createCar(CarRequest carRequest, Long id);

    CarResponse updateCar(UpdateCarRequest carRequest, Long id);

    void deleteCar(Long id);
}
