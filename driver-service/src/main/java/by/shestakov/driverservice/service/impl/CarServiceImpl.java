package by.shestakov.driverservice.service.impl;

import by.shestakov.driverservice.dto.request.CarRequest;
import by.shestakov.driverservice.dto.request.CarUpdateRequest;
import by.shestakov.driverservice.dto.response.CarResponse;
import by.shestakov.driverservice.dto.response.PageResponse;
import by.shestakov.driverservice.entity.Car;
import by.shestakov.driverservice.entity.Driver;
import by.shestakov.driverservice.exception.car.CarNotFoundException;
import by.shestakov.driverservice.exception.car.CarNumberAlreadyException;
import by.shestakov.driverservice.exception.driver.DriverNotFoundException;
import by.shestakov.driverservice.mapper.CarMapper;
import by.shestakov.driverservice.mapper.PageMapper;
import by.shestakov.driverservice.repository.CarRepository;
import by.shestakov.driverservice.repository.DriverRepository;
import by.shestakov.driverservice.service.CarService;
import by.shestakov.driverservice.util.ExceptionMessages;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class CarServiceImpl implements CarService {

    private final CarRepository carRepository;
    private final DriverRepository driverRepository;
    private final CarMapper carMapper;
    private final PageMapper pageMapper;

    @Override
    public PageResponse<CarResponse> getAllCars(Integer offset, Integer limit) {
        log.debug("Entering in getAllCars method. Offset: {}, Limit: {}", offset, limit);
        Page<CarResponse> carPageDto = carRepository
            .findAllByIsDeletedFalse(PageRequest.of(offset, limit))
            .map(carMapper::toDto);
        log.info("getAllCars: Returns all cars. Cars: {}", carPageDto);
        return pageMapper.toDto(carPageDto);
    }

    @Transactional
    @Override
    public CarResponse createCar(CarRequest carRequest, Long driverId) {
        log.debug("Entering in createCar method. CarRequest: {}, DriverId: {}", carRequest, driverId);
        if (carRepository.existsByCarNumber(carRequest.carNumber())) {
            throw new CarNumberAlreadyException(
                ExceptionMessages.CONFLICT_MESSAGE.formatted("car"));
        }

        Driver driver = driverRepository.findByIdAndIsDeletedFalse(driverId)
            .orElseThrow(() -> new DriverNotFoundException(
                ExceptionMessages.NOT_FOUND_MESSAGE.formatted("driver", driverId)));

        Car newCar = carMapper.toEntity(carRequest);
        newCar.setDriver(driver);
        driver.getCars().add(newCar);
        newCar.setIsDeleted(false);

        carRepository.save(newCar);
        driverRepository.save(driver);
        log.info("createCar: Car successfully created and assign to Driver. Car: {}, Driver: {}", newCar, driver);
        return carMapper.toDto(newCar);
    }

    @Transactional
    @Override
    public CarResponse updateCar(CarUpdateRequest carUpdateRequest, Long id) {
        log.debug("Entering in updateCar method. CarUpdateRequest: {}, CarId: {}", carUpdateRequest, id);
        Car existsCar = carRepository.findByIdAndIsDeletedFalse(id)
            .orElseThrow(() -> new CarNotFoundException(
                ExceptionMessages.NOT_FOUND_MESSAGE.formatted("car", id)));

        if (carRepository.existsByCarNumber(carUpdateRequest.carNumber())) {
            log.error("updateCar: CarNumber Already exists. CarNumber: {}", carUpdateRequest.carNumber());
            throw new CarNumberAlreadyException(
                ExceptionMessages.CONFLICT_MESSAGE.formatted("car"));
        }
        carMapper.updateToExists(carUpdateRequest, existsCar);
        carRepository.save(existsCar);

        log.info("updateCar: Car successfully updated. Car: {}", existsCar);
        return carMapper.toDto(existsCar);
    }

    @Transactional
    @Override
    public void deleteCar(Long id) {
        log.debug("Entering in deleteCar method. CarId: {}", id);
        Car existsCar = carRepository.findByIdAndIsDeletedFalse(id)
            .orElseThrow(() -> new CarNotFoundException(
                ExceptionMessages.NOT_FOUND_MESSAGE.formatted("car", id)));
        existsCar.setIsDeleted(true);

        log.info("deleteCar: Car successfully deleted. Car: {}", existsCar);
        carRepository.save(existsCar);
    }

}
