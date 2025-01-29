package by.shestakov.driverservice.service.impl;

import by.shestakov.driverservice.dto.request.CarRequest;
import by.shestakov.driverservice.dto.response.CarResponse;
import by.shestakov.driverservice.entity.Car;
import by.shestakov.driverservice.entity.Driver;
import by.shestakov.driverservice.exception.car.CarNotFoundException;
import by.shestakov.driverservice.exception.car.CarNumberAlreadyException;
import by.shestakov.driverservice.exception.car.CarWasDeletedException;
import by.shestakov.driverservice.exception.driver.DriverNotFoundException;
import by.shestakov.driverservice.mapper.CarMapper;
import by.shestakov.driverservice.repository.CarRepository;
import by.shestakov.driverservice.repository.DriverRepository;
import by.shestakov.driverservice.service.CarService;
import by.shestakov.driverservice.util.ExceptionMessages;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CarServiceImpl implements CarService {

    private final CarRepository carRepository;
    private final CarMapper carMapper;
    private final DriverRepository driverRepository;


    @Override
    public List<CarResponse> getAllCars() {
        return carRepository.findAll().stream().map(carMapper::toDto).collect(Collectors.toList());
    }

    @Transactional
    @Override
    public CarResponse createCar(CarRequest carRequest, Long driverId) {
        if(carRepository.existsByCarNumber(carRequest.carNumber())){
            throw new CarNumberAlreadyException(String.format(ExceptionMessages.CONFLICT_MESSAGE,"car"));
        }
        Car newCar = carMapper.toEntity(carRequest);
        Driver driver = driverRepository.findById(driverId).orElseThrow(() -> new DriverNotFoundException(String.format(ExceptionMessages.NOT_FOUND_MESSAGE,"driver",driverId)));
        newCar.setDriver(driver);
        driver.getCars().add(newCar);
        newCar.setIsDeleted(false);

        carRepository.save(newCar);
        driverRepository.save(driver);

        return carMapper.toDto(newCar);
    }
    @Transactional
    @Override
    public CarResponse updateCar(CarRequest carRequest, Long id) {
        Car existsCar = carRepository.findById(id).orElseThrow(() -> new CarNotFoundException(""));
        if(existsCar.getIsDeleted()){
            throw new CarWasDeletedException(String.format(ExceptionMessages.BAD_REQUEST_MESSAGE,"car"));
        }
        if(carRepository.existsByCarNumber(carRequest.carNumber())){
            throw new CarNumberAlreadyException(String.format(ExceptionMessages.CONFLICT_MESSAGE,"car"));
        }
        carMapper.updateToExists(carRequest,existsCar);
        carRepository.save(existsCar);
        return carMapper.toDto(existsCar);
    }



    @Transactional
    @Override
    public void deleteCar(Long id) {
       Car existsCar = carRepository.findById(id).orElseThrow(() -> new CarNotFoundException(""));
        if(existsCar.getIsDeleted()){
            throw new CarWasDeletedException(String.format(ExceptionMessages.BAD_REQUEST_MESSAGE,"car"));
        }
        existsCar.setIsDeleted(true);
        carRepository.save(existsCar);
    }

}
