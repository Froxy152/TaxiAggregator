package by.shestakov.passengerservice.service;

import by.shestakov.passengerservice.dto.request.PassengerDtoRequest;
import by.shestakov.passengerservice.dto.response.PassengerDtoResponse;

public interface PassengerService {
    public PassengerDtoResponse getById(Long id);

    public PassengerDtoResponse create(PassengerDtoRequest passengerDtoRequest);

    public PassengerDtoResponse updateById(PassengerDtoRequest passengerDtoRequest, Long id);

    public void delete(Long id);

}
