package by.shestakov.ridesservice.service;

import by.shestakov.ridesservice.RidesServiceApplication;
import by.shestakov.ridesservice.dto.request.RideRequest;
import by.shestakov.ridesservice.dto.request.RideStatusRequest;
import by.shestakov.ridesservice.dto.request.RideUpdateRequest;
import by.shestakov.ridesservice.dto.response.PageResponse;
import by.shestakov.ridesservice.dto.response.RideResponse;

public interface RideService {
    PageResponse<RideResponse> getAll(Integer offset, Integer limit);

    RideResponse getById(String id);

    RideResponse createRide(RideRequest rideRequest);

    RideResponse changeStatus(RideStatusRequest statusRequest, String rideId);

    RideResponse updateRide(RideUpdateRequest rideUpdateRequest, String rideId);


}
