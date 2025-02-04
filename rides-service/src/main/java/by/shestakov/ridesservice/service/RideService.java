package by.shestakov.ridesservice.service;

import by.shestakov.ridesservice.dto.request.RideRequest;
import by.shestakov.ridesservice.dto.request.RideStatusRequest;
import by.shestakov.ridesservice.dto.response.PageResponse;
import by.shestakov.ridesservice.dto.response.RideResponse;

public interface RideService {
    public PageResponse<RideResponse> getAll(Integer offset, Integer limit);

    public RideResponse createRide(RideRequest rideRequest);

    public RideResponse changeStatus(RideStatusRequest statusRequest, String rideId);

    public RideResponse updateRide(RideRequest rideRequest, String rideID);
}
