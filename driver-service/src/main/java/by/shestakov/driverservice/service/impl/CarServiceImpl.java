package by.shestakov.driverservice.service.impl;

import by.shestakov.driverservice.dto.CarDto;
import by.shestakov.driverservice.entity.Car;
import by.shestakov.driverservice.entity.Driver;
import by.shestakov.driverservice.mapper.CarMapper;
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
    private final CarMapper carMapper;
    private final DriverRepository driverRepository;
    @Transactional
    @Override
    public CarDto createCar(CarDto carDto, Long driverId) {
        Car newCar = carMapper.toEntity(carDto);
        Driver driver = driverRepository.findById(driverId).orElseThrow();
        newCar.setDriver(driver);
        carRepository.save(newCar);
        return carDto;
    }
    @Transactional
    @Override
    public CarDto updateCar(CarDto carDto, Long id) {
        return null;
    }

    @Transactional
    @Override
    public CarDto deleteCar(Long id) {
        return null;
    }

}
