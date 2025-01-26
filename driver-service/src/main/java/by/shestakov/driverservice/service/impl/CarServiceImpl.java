package by.shestakov.driverservice.service.impl;

import by.shestakov.driverservice.dto.request.CarDtoRequest;
import by.shestakov.driverservice.dto.response.CarDtoResponse;
import by.shestakov.driverservice.entity.Car;
import by.shestakov.driverservice.entity.Driver;
import by.shestakov.driverservice.mapper.CarMapper;
import by.shestakov.driverservice.mapper.impl.CarStructMapper;
import by.shestakov.driverservice.repository.CarRepository;
import by.shestakov.driverservice.repository.DriverRepository;
import by.shestakov.driverservice.service.CarService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class CarServiceImpl implements CarService {
    private final CarRepository carRepository;
    private final CarStructMapper carMapper;
    private final DriverRepository driverRepository;
    @Transactional
    @Override
    public CarDtoResponse createCar(CarDtoRequest carDtoRequest, Long driverId) {
        Car newCar = carMapper.toEntity(carDtoRequest);
        Driver driver = driverRepository.findById(driverId).orElseThrow();
        newCar.setDriver(driver);
        carRepository.save(newCar);
        return carMapper.toDto(newCar);
    }
    @Transactional
    @Override
    public CarDtoResponse updateCar(CarDtoRequest carDtoRequest, Long id) {
        return null;
    }

    @Transactional
    @Override
    public void deleteCar(Long id) {

    }

}
