package by.shestakov.passengerservice.mapper;

import by.shestakov.passengerservice.dto.request.PassengerDtoRequest;
import by.shestakov.passengerservice.dto.response.PassengerDtoResponse;
import by.shestakov.passengerservice.entity.Passenger;

public interface PassengerMapper {
    public PassengerDtoResponse toDto(Passenger passenger);

    public Passenger toEntity(PassengerDtoRequest passengerDtoRequest);

    public void toUpdateExists(PassengerDtoRequest passengerDtoRequest, Passenger passenger);

}
