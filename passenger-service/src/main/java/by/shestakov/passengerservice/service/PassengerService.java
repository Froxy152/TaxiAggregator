package by.shestakov.passengerservice.service;

import by.shestakov.passengerservice.dto.request.PassengerRequest;
import by.shestakov.passengerservice.dto.response.PageResponse;
import by.shestakov.passengerservice.dto.response.PassengerResponse;

public interface PassengerService {
    PageResponse<PassengerResponse> getAllPassengers(Integer offset, Integer limit);

    PassengerResponse getPassengerById(Long id);

    PassengerResponse createPassenger(PassengerRequest passengerRequest);

    PassengerResponse updatePassengerById(PassengerRequest passengerRequest, Long id);

    void softDeletePassenger(Long id);
}
