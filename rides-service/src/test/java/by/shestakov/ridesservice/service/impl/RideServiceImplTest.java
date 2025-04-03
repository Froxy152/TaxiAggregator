package by.shestakov.ridesservice.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static testConstant.TestConstant.TEST_DESTINATION_ADDRESS;
import static testConstant.TestConstant.TEST_DRIVER;
import static testConstant.TestConstant.TEST_DRIVER_RESPONSE;
import static testConstant.TestConstant.TEST_DRIVER_WITHOUT_CAR;
import static testConstant.TestConstant.TEST_ID;
import static testConstant.TestConstant.TEST_PASSENGER;
import static testConstant.TestConstant.TEST_PASSENGER_RESPONSE;
import static testConstant.TestConstant.TEST_PICKUP_ADDRESS;
import static testConstant.TestConstant.TEST_ROUTING_RESPONSE;
import static testConstant.TestConstant.defaultRide;
import static testConstant.TestConstant.defaultRideRequest;
import static testConstant.TestConstant.defaultRideResponse;
import static testConstant.TestConstant.defaultRideStatusRequest;
import static testConstant.TestConstant.defaultRideUpdateRequest;
import static testConstant.TestConstant.updatedRideResponse;

import by.shestakov.ridesservice.dto.request.RideRequest;
import by.shestakov.ridesservice.dto.request.RideStatusRequest;
import by.shestakov.ridesservice.dto.request.RideUpdateRequest;
import by.shestakov.ridesservice.dto.response.DriverResponse;
import by.shestakov.ridesservice.dto.response.PageResponse;
import by.shestakov.ridesservice.dto.response.RideResponse;
import by.shestakov.ridesservice.dto.response.RoutingResponse;
import by.shestakov.ridesservice.entity.Driver;
import by.shestakov.ridesservice.entity.Passenger;
import by.shestakov.ridesservice.entity.Ride;
import by.shestakov.ridesservice.exception.DataNotFoundException;
import by.shestakov.ridesservice.exception.DriverWithoutCarException;
import by.shestakov.ridesservice.exception.FeignNotFoundDataException;
import by.shestakov.ridesservice.feign.DriverClient;
import by.shestakov.ridesservice.feign.PassengerClient;
import by.shestakov.ridesservice.mapper.DriverMapper;
import by.shestakov.ridesservice.mapper.PageMapper;
import by.shestakov.ridesservice.mapper.PassengerMapper;
import by.shestakov.ridesservice.mapper.RideMapper;
import by.shestakov.ridesservice.repository.RideRepository;
import by.shestakov.ridesservice.util.constant.ExceptionMessage;
import feign.RetryableException;
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
class RideServiceImplTest {

    @InjectMocks
    private RideServiceImpl rideService;

    @Mock
    private RideRepository rideRepository;

    @Mock
    private RideMapper rideMapper;

    @Mock
    private PageMapper pageMapper;

    @Mock
    private RouteServiceImpl routeService;

    @Mock
    private DriverClient driverClient;

    @Mock
    private PassengerClient passengerClient;

    @Mock
    private DriverMapper driverMapper;

    @Mock
    private PassengerMapper passengerMapper;

    @Test
    void getAll() {
        int offset = 0;
        int limit = 3;

        List<Ride> rides = List.of(defaultRide());
        Page<Ride> ridePage = new PageImpl<>(rides, PageRequest.of(offset, limit), rides.size());
        List<RideResponse> rideResponses = List.of(defaultRideResponse());
        Page<RideResponse> rideResponsePage =
                new PageImpl<>(rideResponses, PageRequest.of(offset, limit), rideResponses.size());
        PageResponse<RideResponse> expectedResponse =
                new PageResponse<>(offset, limit, 1, rideResponses.size(), "", rideResponses);

        when(rideRepository.findAll(PageRequest.of(offset, limit))).thenReturn(ridePage);
        when(rideMapper.toDto(any(Ride.class))).thenReturn(defaultRideResponse());
        when(pageMapper.toDto(rideResponsePage)).thenReturn(expectedResponse);

        PageResponse<RideResponse> response = rideService.getAll(offset, limit);

        assertNotNull(response);
        assertEquals(expectedResponse, response);

        verify(rideRepository).findAll(PageRequest.of(offset, limit));
        verify(rideMapper, times(rides.size())).toDto(any(Ride.class));
        verify(pageMapper).toDto(rideResponsePage);
    }

    @Test
    void getById_ReturnsValidResponse() {
        String id = TEST_ID;
        Ride ride = defaultRide();
        RideResponse expectedResponse = defaultRideResponse();

        when(rideRepository.findById(TEST_ID)).thenReturn(Optional.of(ride));
        when(rideMapper.toDto(ride)).thenReturn(expectedResponse);

        RideResponse response = rideService.getById(id);

        assertNotNull(response);
        assertEquals(expectedResponse, response);

        verify(rideRepository).findById(id);
        verify(rideMapper).toDto(ride);
    }

    @Test
    void getById_RideNotFound_ThrowException() {
        String id = TEST_ID;

        when(rideRepository.findById(TEST_ID)).thenReturn(Optional.empty());

        DataNotFoundException exception = assertThrows(
                DataNotFoundException.class,
                () -> rideService.getById(id)
        );

        assertEquals(ExceptionMessage.NOT_FOUND_MESSAGE, exception.getMessage());

        verify(rideRepository).findById(id);
    }

    @Test
    void createRide_ReturnsValidResponse() {
        RideRequest request = defaultRideRequest();
        Driver driver = TEST_DRIVER;
        Passenger passenger = TEST_PASSENGER;
        Ride ride = defaultRide();
        RideResponse expectedResponse = defaultRideResponse();
        RoutingResponse routingResponse = TEST_ROUTING_RESPONSE;


        when(driverClient.getDriverById(request.driverId())).thenReturn(TEST_DRIVER_RESPONSE);
        when(driverMapper.toEntity(TEST_DRIVER_RESPONSE)).thenReturn(driver);
        when(passengerClient.getPassengerById(request.passengerId())).thenReturn(TEST_PASSENGER_RESPONSE);
        when(passengerMapper.toEntity(TEST_PASSENGER_RESPONSE)).thenReturn(passenger);
        when(routeService.createRequest(TEST_PICKUP_ADDRESS, TEST_DESTINATION_ADDRESS)).thenReturn(routingResponse);
        when(rideMapper.toEntity(request)).thenReturn(ride);
        when(rideMapper.toDto(ride)).thenReturn(expectedResponse);

        RideResponse response = rideService.createRide(request);

        assertNotNull(response);
        assertEquals(expectedResponse, response);

        verify(routeService).createRequest(TEST_PICKUP_ADDRESS, TEST_DESTINATION_ADDRESS);
        verify(rideMapper).toEntity(request);
        verify(rideMapper).toDto(ride);
        verify(rideRepository).save(ride);
    }

    @Test
    void createRide_DriverWithoutCar_ThrowException() {
        RideRequest request = defaultRideRequest();
        Driver driver = TEST_DRIVER_WITHOUT_CAR;


        when(driverClient.getDriverById(request.driverId())).thenReturn(TEST_DRIVER_RESPONSE);
        when(driverMapper.toEntity(TEST_DRIVER_RESPONSE)).thenReturn(driver);

        DriverWithoutCarException exception = assertThrows(
                DriverWithoutCarException.class,
                () -> rideService.createRide(request)
        );

        assertEquals(ExceptionMessage.DRIVER_WITHOUT_CAR, exception.getMessage());

        verify(driverClient).getDriverById(request.driverId());
        verify(driverMapper).toEntity(TEST_DRIVER_RESPONSE);
    }

    @Test
    void createRide_DriverNotFound_ThrowException() {
        RideRequest request = defaultRideRequest();

        when(driverClient.getDriverById(request.driverId())).thenThrow(FeignNotFoundDataException.class);

        assertThrows(
                FeignNotFoundDataException.class,
                () -> rideService.createRide(request)
        );

        verify(driverClient).getDriverById(request.driverId());
    }

    @Test
    void createRide_DriverServiceUnavailable_ThrowException() {
        RideRequest request = defaultRideRequest();

        when(driverClient.getDriverById(request.driverId())).thenThrow(RetryableException.class);

        assertThrows(
                RetryableException.class,
                () -> rideService.createRide(request)
        );

        verify(driverClient).getDriverById(request.driverId());
    }

    @Test
    void createRide_PassengerNotFound_ThrowException() {
        RideRequest request = defaultRideRequest();
        Driver driver = TEST_DRIVER;
        DriverResponse response = TEST_DRIVER_RESPONSE;

        when(driverClient.getDriverById(request.driverId())).thenReturn(response);
        when(driverMapper.toEntity(response)).thenReturn(driver);
        when(passengerClient.getPassengerById(request.passengerId())).thenThrow(FeignNotFoundDataException.class);

        assertThrows(
                FeignNotFoundDataException.class,
                () -> rideService.createRide(request)
        );

        verify(driverClient).getDriverById(request.driverId());
        verify(driverMapper).toEntity(response);
        verify(passengerClient).getPassengerById(request.passengerId());
    }

    @Test
    void createRide_PassengerServiceUnavailable_ThrowException() {
        RideRequest request = defaultRideRequest();
        Driver driver = TEST_DRIVER;
        DriverResponse response = TEST_DRIVER_RESPONSE;

        when(driverClient.getDriverById(request.driverId())).thenReturn(response);
        when(driverMapper.toEntity(response)).thenReturn(driver);
        when(passengerClient.getPassengerById(request.passengerId())).thenThrow(RetryableException.class);

        assertThrows(
                RetryableException.class,
                () -> rideService.createRide(request)
        );

        verify(driverClient).getDriverById(request.driverId());
        verify(driverMapper).toEntity(response);
        verify(passengerClient).getPassengerById(request.passengerId());
    }

    @Test
    void changeStatus_ReturnValidResponse() {
        String rideId = TEST_ID;
        Ride ride = defaultRide();
        RideStatusRequest request = defaultRideStatusRequest();
        RideResponse expectedResponse = defaultRideResponse();

        when(rideRepository.findById(rideId)).thenReturn(Optional.of(ride));
        when(rideMapper.toDto(ride)).thenReturn(expectedResponse);

        RideResponse response = rideService.changeStatus(request, rideId);

        assertEquals(expectedResponse.status(), response.status());

        verify(rideRepository).findById(rideId);
        verify(rideRepository).save(ride);
        verify(rideMapper).toDto(ride);
    }

    @Test
    void changeStatus_RideNotFound_ThrowException() {
        String rideId = TEST_ID;

        RideStatusRequest request = defaultRideStatusRequest();

        when(rideRepository.findById(rideId)).thenReturn(Optional.empty());

        DataNotFoundException exception = assertThrows(
                DataNotFoundException.class,
                () -> rideService.changeStatus(request, rideId)

        );

        assertEquals(ExceptionMessage.NOT_FOUND_MESSAGE, exception.getMessage());

    }

    @Test
    void updateRide_ReturnsValidResponse() {
        String rideId = TEST_ID;
        Ride ride = defaultRide();
        RideUpdateRequest request = defaultRideUpdateRequest();
        RoutingResponse routingResponse = TEST_ROUTING_RESPONSE;
        RideResponse expectedResponse = updatedRideResponse();

        when(rideRepository.findById(rideId)).thenReturn(Optional.of(ride));
        when(routeService.createRequest(TEST_PICKUP_ADDRESS, TEST_DESTINATION_ADDRESS)).thenReturn(routingResponse);
        when(rideMapper.toDto(ride)).thenReturn(expectedResponse);

        RideResponse response = rideService.updateRide(request, rideId);

        assertNotNull(response);
        assertEquals(expectedResponse, response);

        verify(rideRepository).findById(rideId);
        verify(routeService).createRequest(TEST_PICKUP_ADDRESS, TEST_DESTINATION_ADDRESS);
        verify(rideMapper).updateExists(request, ride);
        verify(rideRepository).save(ride);
        verify(rideMapper).toDto(ride);
    }

    @Test
    void updateRide_RideNotFound_ThrowException() {
        String rideId = TEST_ID;
        RideUpdateRequest request = defaultRideUpdateRequest();

        when(rideRepository.findById(rideId)).thenReturn(Optional.empty());

        DataNotFoundException exception = assertThrows(
                DataNotFoundException.class,
                () -> rideService.updateRide(request, rideId)
        );

        assertEquals(ExceptionMessage.NOT_FOUND_MESSAGE, exception.getMessage());

        verify(rideRepository).findById(rideId);
    }
}