package by.shestakov.passengerservice.service;

import by.shestakov.passengerservice.dto.request.PassengerRequest;
import by.shestakov.passengerservice.dto.response.PageResponse;
import by.shestakov.passengerservice.dto.response.PassengerResponse;

public interface PassengerService {
    public PageResponse<PassengerResponse> getAllPassengers(Integer offset, Integer limit);

    public PassengerResponse getPassengerById(Long id);

    public PassengerResponse createPassenger(PassengerRequest passengerRequest);

    public PassengerResponse updatePassengerById(PassengerRequest passengerRequest, Long id);

    public void softDeletePassenger(Long id);

}
