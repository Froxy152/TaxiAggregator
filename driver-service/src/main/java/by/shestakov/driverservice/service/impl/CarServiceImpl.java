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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class CarServiceImpl implements CarService {

    private final CarRepository carRepository;
    private final DriverRepository driverRepository;
    private final CarMapper carMapper;
    private final PageMapper pageMapper;

    @Override
    public PageResponse<CarResponse> getAllCars(Integer offset, Integer limit) {
        Page<CarResponse> carPageDto = carRepository
                .findAllByIsDeletedFalse(PageRequest.of(offset, limit))
                .map(carMapper::toDto);

        return pageMapper.toDto(carPageDto);
    }

    @Transactional
    @Override
    public CarResponse createCar(CarRequest carRequest, Long driverId) {
        if (carRepository.existsByCarNumber(carRequest.carNumber())) {
            throw new CarNumberAlreadyException(
                    ExceptionMessages.CONFLICT_MESSAGE.formatted("car"));
        }
        Car newCar = carMapper.toEntity(carRequest);
        Driver driver = driverRepository.findById(driverId)
                .orElseThrow(() -> new DriverNotFoundException(
                        ExceptionMessages.NOT_FOUND_MESSAGE.formatted("driver", driverId)));
        newCar.setDriverId(driver);
        driver.getCars().add(newCar);
        newCar.setIsDeleted(false);

        carRepository.save(newCar);
        driverRepository.save(driver);

        return carMapper.toDto(newCar);
    }

    @Transactional
    @Override
    public CarResponse updateCar(CarUpdateRequest carUpdateRequest, Long id) {
        Car existsCar = carRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new CarNotFoundException(
                        ExceptionMessages.NOT_FOUND_MESSAGE.formatted("car", id)));

        if (carRepository.existsByCarNumber(carUpdateRequest.carNumber())) {
            throw new CarNumberAlreadyException(
                    ExceptionMessages.CONFLICT_MESSAGE.formatted("car"));
        }
        carMapper.updateToExists(carUpdateRequest, existsCar);
        carRepository.save(existsCar);

        return carMapper.toDto(existsCar);
    }

    @Transactional
    @Override
    public void deleteCar(Long id) {
        Car existsCar = carRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new CarNotFoundException(
                        ExceptionMessages.NOT_FOUND_MESSAGE.formatted("car", id)));
        existsCar.setIsDeleted(true);

        carRepository.save(existsCar);
    }

}
