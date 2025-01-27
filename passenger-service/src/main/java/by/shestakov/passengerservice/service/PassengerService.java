package by.shestakov.passengerservice.service;

import by.shestakov.passengerservice.dto.request.PassengerRequest;
import by.shestakov.passengerservice.dto.response.PassengerResponse;

public interface PassengerService {
    public PassengerResponse getById(Long id);

    public PassengerResponse create(PassengerRequest passengerRequest);

    public PassengerResponse updateById(PassengerRequest passengerRequest, Long id);

    public void delete(Long id);

}
