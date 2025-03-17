package by.shestakov.driverservice.service.impl;

import static by.shestakov.constant.TestCarData.TEST_CAR_ID;
import static by.shestakov.constant.TestCarData.TEST_DRIVER_ID;
import static by.shestakov.constant.TestCarData.defaultCar;
import static by.shestakov.constant.TestCarData.defaultCarRequest;
import static by.shestakov.constant.TestCarData.defaultCarResponse;
import static by.shestakov.constant.TestCarData.updateRequest;
import static by.shestakov.constant.TestCarData.updatedResponse;
import static by.shestakov.constant.TestDriverData.defaultDriver;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
import by.shestakov.driverservice.util.ExceptionMessages;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

@ExtendWith(MockitoExtension.class)
class CarServiceImplTest {

    @Mock
    private CarRepository carRepository;

    @Mock
    private DriverRepository driverRepository;

    @Mock
    private CarMapper carMapper;

    @Mock
    private PageMapper pageMapper;

    @InjectMocks
    private CarServiceImpl carService;

    @Test
    void getAllCars() {
        int offset = 0;
        int limit = 2;

        List<Car> cars = List.of(defaultCar());
        Page<Car> carsPage = new PageImpl<>(cars, PageRequest.of(offset, limit), cars.size());
        List<CarResponse> carsResponse = List.of(defaultCarResponse());
        Page<CarResponse> carsResponsePage =
            new PageImpl<>(carsResponse, PageRequest.of(offset, limit), carsResponse.size());
        PageResponse<CarResponse> expectedResponse =
            new PageResponse<>(offset, limit, 1, carsResponse.size(), "", carsResponse);

        when(carRepository.findAllByIsDeletedFalse(PageRequest.of(offset, limit))).thenReturn(carsPage);
        when(carMapper.toDto(any(Car.class))).thenReturn(defaultCarResponse());
        when(pageMapper.toDto(carsResponsePage)).thenReturn(expectedResponse);

        PageResponse<CarResponse> response = carService.getAllCars(offset, limit);

        assertNotNull(response);
        assertEquals(expectedResponse, response);

        verify(carRepository).findAllByIsDeletedFalse(PageRequest.of(offset, limit));
        verify(carMapper, times(cars.size())).toDto(any(Car.class));
        verify(pageMapper).toDto(carsResponsePage);
    }

    @Test
    void createCar_ReturnsValidResponse() {
        CarRequest request = defaultCarRequest();
        CarResponse expectedResponse = defaultCarResponse();
        Car car = defaultCar();
        Long driverId = TEST_DRIVER_ID;
        Driver driver = defaultDriver();

        when(carRepository.existsByCarNumber(request.carNumber())).thenReturn(false);
        when(driverRepository.findByIdAndIsDeletedFalse(driverId)).thenReturn(Optional.of(driver));
        when(carMapper.toEntity(request)).thenReturn(car);
        when(carMapper.toDto(car)).thenReturn(expectedResponse);

        CarResponse response = carService.createCar(request, driverId);

        assertNotNull(response);
        assertEquals(expectedResponse, response);
        assertFalse(car.getIsDeleted());

        verify(carRepository).existsByCarNumber(request.carNumber());
        verify(driverRepository).findByIdAndIsDeletedFalse(driverId);
        verify(carMapper).toEntity(request);
        verify(carRepository).save(car);
        verify(driverRepository).save(driver);
    }

    @Test
    void createCar_CarExists_ThrowException() {
        CarRequest request = defaultCarRequest();
        Long driverId = TEST_DRIVER_ID;

        when(carRepository.existsByCarNumber(request.carNumber())).thenReturn(true);

        CarNumberAlreadyException exception = assertThrows(
            CarNumberAlreadyException.class,
            () -> carService.createCar(request, driverId)
        );

        assertEquals(ExceptionMessages.CONFLICT_MESSAGE.formatted("car"), exception.getMessage());

        verify(carRepository).existsByCarNumber(request.carNumber());
    }

    @Test
    void createCar_DriverNotFound_ThrowException() {
        Long driverId = TEST_DRIVER_ID;
        CarRequest request = defaultCarRequest();

        when(carRepository.existsByCarNumber(request.carNumber())).thenReturn(false);
        when(driverRepository.findByIdAndIsDeletedFalse(driverId)).thenReturn(Optional.empty());

        DriverNotFoundException exception = assertThrows(
            DriverNotFoundException.class,
            () -> carService.createCar(request, driverId)
        );

        assertEquals(ExceptionMessages.NOT_FOUND_MESSAGE.formatted("driver", driverId), exception.getMessage());

    }

    @Test
    void updateCar_ReturnsValidResponse() {
        CarUpdateRequest request = updateRequest();
        CarResponse expectedResponse = updatedResponse();
        Car car = defaultCar();
        Long id = TEST_CAR_ID;

        when(carRepository.findByIdAndIsDeletedFalse(id)).thenReturn(Optional.of(car));
        when(carRepository.existsByCarNumber(request.carNumber())).thenReturn(false);
        when(carMapper.toDto(car)).thenReturn(updatedResponse());

        CarResponse response = carService.updateCar(request, id);

        assertNotNull(response);
        assertEquals(expectedResponse, response);

        verify(carRepository).findByIdAndIsDeletedFalse(id);
        verify(carRepository).existsByCarNumber(request.carNumber());
        verify(carMapper).updateToExists(request, car);
        verify(carRepository).save(car);
        verify(carMapper).toDto(car);
    }

    @Test
    void updateCar_CarNotFound_ThrowException() {
        Long id = TEST_CAR_ID;
        CarUpdateRequest request = updateRequest();

        when(carRepository.findByIdAndIsDeletedFalse(id)).thenReturn(Optional.empty());

        CarNotFoundException exception = assertThrows(
            CarNotFoundException.class,
            () -> carService.updateCar(request, id)
        );

        assertEquals(ExceptionMessages.NOT_FOUND_MESSAGE.formatted("car", id), exception.getMessage());
    }

    @Test
    void updateCar_CarNumberAlreadyExists_ThrowException() {
        Long id = TEST_CAR_ID;
        CarUpdateRequest request = updateRequest();
        Car car = defaultCar();

        when(carRepository.findByIdAndIsDeletedFalse(id)).thenReturn(Optional.of(car));
        when(carRepository.existsByCarNumber(request.carNumber())).thenReturn(true);

        CarNumberAlreadyException exception = assertThrows(
            CarNumberAlreadyException.class,
            () -> carService.updateCar(request, id)
        );

        assertEquals(ExceptionMessages.CONFLICT_MESSAGE.formatted("car"), exception.getMessage());
    }

    @Test
    void deleteCar_SuccessfulDelete() {
        Long id = TEST_CAR_ID;
        Car car = defaultCar();

        when(carRepository.findByIdAndIsDeletedFalse(id)).thenReturn(Optional.of(car));

        carService.deleteCar(id);

        assertTrue(car.getIsDeleted());

        verify(carRepository).save(car);
    }

    @Test
    void deleteCar_CarNotFound_ThrowException() {
        Long id = TEST_CAR_ID;

        when(carRepository.findByIdAndIsDeletedFalse(id)).thenReturn(Optional.empty());

        CarNotFoundException exception = assertThrows(
            CarNotFoundException.class,
            () -> carService.deleteCar(id)
        );

        assertEquals(ExceptionMessages.NOT_FOUND_MESSAGE.formatted("car", id), exception.getMessage());
    }
}