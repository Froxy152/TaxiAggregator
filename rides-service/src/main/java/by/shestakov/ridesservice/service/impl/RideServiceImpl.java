package by.shestakov.ridesservice.service.impl;

import by.shestakov.ridesservice.dto.request.RideRequest;
import by.shestakov.ridesservice.dto.request.RideStatusRequest;
import by.shestakov.ridesservice.dto.request.RoutingRequest;
import by.shestakov.ridesservice.dto.response.PageResponse;
import by.shestakov.ridesservice.dto.response.RideResponse;
import by.shestakov.ridesservice.dto.response.RoutingResponse;
import by.shestakov.ridesservice.entity.Ride;
import by.shestakov.ridesservice.feign.RoutingFeign;
import by.shestakov.ridesservice.mapper.PageMapper;
import by.shestakov.ridesservice.mapper.RideMapper;
import by.shestakov.ridesservice.repository.RideRepository;
import by.shestakov.ridesservice.service.RideService;
import by.shestakov.ridesservice.util.constant.ApiConstant;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class RideServiceImpl implements RideService {
    private final RideRepository rideRepository;
    private final RideMapper rideMapper;
    private final PageMapper pageMapper;
    private final RoutingFeign routingFeign;


    @Override
    public PageResponse<RideResponse> getAll(Integer offset, Integer limit) {
        Page<RideResponse> rides = rideRepository.findAll(PageRequest.of(offset, limit))
                .map(rideMapper::toDto);
        return pageMapper.toDto(rides);
    }

    @Override
    public RideResponse createRide(RideRequest rideRequest) {


        RoutingRequest request = RoutingRequest.builder()
                .points(List.of(rideRequest.addressFrom(), rideRequest.addressDestination()))
                .calc_point(false)
                .key(ApiConstant.)
                .build();

        System.out.println(request.toString());
        RoutingResponse response = routingFeign.requestDistance(request.points(), request.calc_point(), request.key());

        System.out.println(response.toString());
        Ride newRide = rideMapper.toEntity(rideRequest);

        newRide.setDistance(response.paths().getFirst().distance());
        newRide.setDuringRide(response.paths().getFirst().time());
        newRide.setTime(LocalDateTime.now());
        newRide.setPrice(1.2);

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

        rideMapper.updateExists(rideRequest, existsRide);

        return rideMapper.toDto(existsRide);
    }
}
