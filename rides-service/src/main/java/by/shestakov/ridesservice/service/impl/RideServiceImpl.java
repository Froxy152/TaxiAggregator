package by.shestakov.ridesservice.service.impl;

import by.shestakov.ridesservice.dto.request.RideRequest;
import by.shestakov.ridesservice.dto.request.RideStatusRequest;
import by.shestakov.ridesservice.dto.response.PageResponse;
import by.shestakov.ridesservice.dto.response.RideResponse;
import by.shestakov.ridesservice.dto.response.RoutingResponse;
import by.shestakov.ridesservice.entity.Ride;
import by.shestakov.ridesservice.mapper.PageMapper;
import by.shestakov.ridesservice.mapper.RideMapper;
import by.shestakov.ridesservice.repository.RideRepository;
import by.shestakov.ridesservice.service.RideService;
import by.shestakov.ridesservice.service.RouteService;
import by.shestakov.ridesservice.util.CalculatePrice;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class RideServiceImpl implements RideService {
    private final RideRepository rideRepository;
    private final RideMapper rideMapper;
    private final PageMapper pageMapper;
    private final RouteService routeService;


    @Override
    public PageResponse<RideResponse> getAll(Integer offset, Integer limit) {
        Page<RideResponse> rides = rideRepository.findAll(PageRequest.of(offset, limit))
                .map(rideMapper::toDto);
        return pageMapper.toDto(rides);
    }

    @Override
    public RideResponse createRide(RideRequest rideRequest) {

        RoutingResponse response = routeService.createRequest(rideRequest.addressFrom(), rideRequest.addressDestination());

        Ride newRide = rideMapper.toEntity(rideRequest);

        Double distance = CalculatePrice.meterToKilometers(response.paths().getFirst().distance());
        Integer time = CalculatePrice.msToMin(response.paths().getFirst().time());


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

        RoutingResponse response = routeService.createRequest(rideRequest.addressFrom(), rideRequest.addressDestination());

        rideMapper.updateExists(rideRequest, existsRide);
        existsRide.setDistance(response.paths().getFirst().distance());
        existsRide.setDuringRide(response.paths().getFirst().time());

        rideRepository.save(existsRide);

        return rideMapper.toDto(existsRide);
    }


}
