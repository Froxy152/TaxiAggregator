package by.shestakov.ridesservice.service.impl;

import by.shestakov.ridesservice.dto.request.RideRequest;
import by.shestakov.ridesservice.dto.request.RideStatusRequest;
import by.shestakov.ridesservice.dto.request.RideUpdateRequest;
import by.shestakov.ridesservice.dto.response.DriverResponse;
import by.shestakov.ridesservice.dto.response.PageResponse;
import by.shestakov.ridesservice.dto.response.PassengerResponse;
import by.shestakov.ridesservice.dto.response.RideResponse;
import by.shestakov.ridesservice.dto.response.RoutingResponse;
import by.shestakov.ridesservice.entity.Driver;
import by.shestakov.ridesservice.entity.Passenger;
import by.shestakov.ridesservice.entity.Ride;
import by.shestakov.ridesservice.exception.DataNotFoundException;
import by.shestakov.ridesservice.exception.DriverWithoutCarException;
import by.shestakov.ridesservice.feign.DriverClient;
import by.shestakov.ridesservice.feign.PassengerClient;
import by.shestakov.ridesservice.mapper.DriverMapper;
import by.shestakov.ridesservice.mapper.PageMapper;
import by.shestakov.ridesservice.mapper.PassengerMapper;
import by.shestakov.ridesservice.mapper.RideMapper;
import by.shestakov.ridesservice.repository.RideRepository;
import by.shestakov.ridesservice.service.RideService;
import by.shestakov.ridesservice.service.RouteService;
import by.shestakov.ridesservice.util.CalculatePrice;
import io.github.resilience4j.retry.annotation.Retry;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class RideServiceImpl implements RideService {

    private final RideRepository rideRepository;

    private final RideMapper rideMapper;

    private final PageMapper pageMapper;

    private final RouteService routeService;

    private final PassengerClient passengerClient;

    private final PassengerMapper passengerMapper;

    private final DriverClient driverClient;

    private final DriverMapper driverMapper;


    @Override
    public PageResponse<RideResponse> getAll(Integer offset, Integer limit) {
        log.debug("Entering getAll method. Offset: {}, Limit: {}", offset, limit);
        Page<RideResponse> rides = rideRepository.findAll(PageRequest.of(offset, limit))
                .map(rideMapper::toDto);
        log.info("getAll: Returns all rides. Rides: {}", rides);
        return pageMapper.toDto(rides);
    }

    @Override
    public RideResponse getById(String id) {
        log.debug("Entering getById method. Id: {}", id);
        Ride existsRide = rideRepository.findById(id).orElseThrow(DataNotFoundException::new);
        log.info("getById: Ride found. RideId: {}", id);
        return rideMapper.toDto(existsRide);
    }

    @Retry(name = "createRideRetry")
    @Override
    public RideResponse createRide(RideRequest rideRequest) {
        log.debug("Entering in createRide method. RideRequest: {}", rideRequest);
        Driver existsDriver = getDriver(rideRequest.driverId());

        if (existsDriver.getCarIds().isEmpty()) {
            log.error("createRide: Driver doesn't have a car");
            throw new DriverWithoutCarException();
        }

        Passenger existsPassenger = getPassenger(rideRequest.passengerId());

        RoutingResponse response = routeService.createRequest(
                rideRequest.pickUpAddress(), rideRequest.destinationAddress());

        Ride newRide = rideMapper.toEntity(rideRequest);

        Double distance = CalculatePrice.meterToKilometers(response.paths().getFirst().distance());
        Integer time = CalculatePrice.msToMin(response.paths().getFirst().time());

        newRide.setDriver(existsDriver);
        newRide.setPassenger(existsPassenger);
        newRide.setDistance(distance);
        newRide.setDuringRide(time);
        newRide.setTime(LocalDateTime.now());
        newRide.setPrice(CalculatePrice.mathPrice(distance, time));


        rideRepository.save(newRide);

        log.info("createRide: Ride successfully created. Ride: {}", newRide);
        return rideMapper.toDto(newRide);
    }

    @Override
    public RideResponse changeStatus(RideStatusRequest statusRequest, String rideId) {
        log.debug("Entering in changeStatus method. RideStatus: {}, RideId: {}", statusRequest, rideId);
        Ride existsRide = rideRepository.findById(rideId).orElseThrow(DataNotFoundException::new);

        existsRide.setStatus(statusRequest.status());
        rideRepository.save(existsRide);

        log.info("changeStatus: Ride status successfully changed. Ride: {}", existsRide);
        return rideMapper.toDto(existsRide);
    }

    @Retry(name = "createRideRetry")
    @Override
    public RideResponse updateRide(RideUpdateRequest rideUpdateRequest, String rideId) {
        log.debug("Entering updateRide method. RideUpdateRequest: {}, RideId: {}", rideUpdateRequest, rideId);
        Ride existsRide = rideRepository.findById(rideId).orElseThrow(DataNotFoundException::new);

        RoutingResponse response = routeService.createRequest(
                rideUpdateRequest.pickUpAddress(), rideUpdateRequest.destinationAddress());

        rideMapper.updateExists(rideUpdateRequest, existsRide);
        existsRide.setDistance(CalculatePrice.meterToKilometers(response.paths().getFirst().distance()));
        existsRide.setDuringRide(CalculatePrice.msToMin(response.paths().getFirst().time()));
        existsRide.setPrice(CalculatePrice.mathPrice(existsRide.getDistance(), existsRide.getDuringRide()));

        rideRepository.save(existsRide);

        log.info("updateRide: Ride successfully updated. Ride: {}", existsRide);
        return rideMapper.toDto(existsRide);
    }

    private Passenger getPassenger(Long id) {
        PassengerResponse passengerResponse = passengerClient.getPassengerById(id);
        log.info("Passenger successfully received. Passenger: {}", passengerResponse);
        return passengerMapper.toEntity(passengerResponse);
    }

    private Driver getDriver(Long id) {
        DriverResponse driverResponse = driverClient.getDriverById(id);
        log.info("Driver successfully received. Passenger: {}", driverResponse);
        return driverMapper.toEntity(driverResponse);
    }

}
