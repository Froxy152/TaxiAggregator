package by.shestakov.passengerservice.service;

import by.shestakov.passengerservice.dto.request.PassengerRequest;
import by.shestakov.passengerservice.dto.response.PassengerResponse;

import java.util.List;

public interface PassengerService {
    public List<PassengerResponse> getAllPassengers();

    public PassengerResponse getPassengerById(Long id);

    public PassengerResponse createPassenger(PassengerRequest passengerRequest);

    public PassengerResponse updatePassengerById(PassengerRequest passengerRequest, Long id);

    public void softDeletePassenger(Long id);

}
