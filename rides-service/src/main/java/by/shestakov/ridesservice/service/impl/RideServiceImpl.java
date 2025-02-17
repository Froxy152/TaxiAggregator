package by.shestakov.ridesservice.service.impl;

import by.shestakov.ridesservice.dto.request.RideRequest;
import by.shestakov.ridesservice.dto.request.RideStatusRequest;
import by.shestakov.ridesservice.dto.response.*;
import by.shestakov.ridesservice.entity.Passenger;
import by.shestakov.ridesservice.entity.Ride;
import by.shestakov.ridesservice.feign.DriverClient;
import by.shestakov.ridesservice.feign.PassengerClient;
import by.shestakov.ridesservice.mapper.PageMapper;
import by.shestakov.ridesservice.mapper.PassengerMapper;
import by.shestakov.ridesservice.mapper.RideMapper;
import by.shestakov.ridesservice.repository.RideRepository;
import by.shestakov.ridesservice.service.RideService;
import by.shestakov.ridesservice.service.RouteService;
import by.shestakov.ridesservice.util.CalculatePrice;

import java.sql.Driver;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;


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


    @Override
    public PageResponse<RideResponse> getAll(Integer offset, Integer limit) {
        Page<RideResponse> rides = rideRepository.findAll(PageRequest.of(offset, limit))
                .map(rideMapper::toDto);
        return pageMapper.toDto(rides);
    }

    @Override
    public RideResponse createRide(RideRequest rideRequest) {

        Passenger existsPassenger = getPassenger(rideRequest.passengerId());


        RoutingResponse response = routeService.createRequest(
                rideRequest.pickUpAddress(), rideRequest.destinationAddress());

        Ride newRide = rideMapper.toEntity(rideRequest);

        Double distance = CalculatePrice.meterToKilometers(response.paths().getFirst().distance());
        Integer time = CalculatePrice.msToMin(response.paths().getFirst().time());


        newRide.setPassengerId(existsPassenger);
        newRide.setDistance(distance);
        newRide.setDuringRide(time);
        newRide.setTime(LocalDateTime.now());
        newRide.setPrice(CalculatePrice.mathPrice(distance, time));

        rideRepository.save(newRide);

        return rideMapper.toDto(newRide);
    }

    @Override
    public RideResponse changeStatus(RideStatusRequest statusRequest, String rideId) {
        Ride existsRide = rideRepository.findById(rideId).orElseThrow();

        existsRide.setStatus(statusRequest.status());
        rideRepository.save(existsRide);

        return rideMapper.toDto(existsRide);
    }

    @Override
    public RideResponse updateRide(RideRequest rideRequest, String rideId) {
        Ride existsRide = rideRepository.findById(rideId).orElseThrow();

        RoutingResponse response = routeService.createRequest(
                rideRequest.pickUpAddress(), rideRequest.destinationAddress());

        rideMapper.updateExists(rideRequest, existsRide);
        existsRide.setDistance(response.paths().getFirst().distance());
        existsRide.setDuringRide(response.paths().getFirst().time());

        rideRepository.save(existsRide);

        return rideMapper.toDto(existsRide);
    }

    private Passenger getPassenger(Long id) {
        PassengerResponse passengerResponse = passengerClient.getPassengerById(id);
        return passengerMapper.toEntity(passengerResponse);
    }

//    private Driver getDriver(Long id) {
//        driverClient.getDriverById(id);
//    }


}
