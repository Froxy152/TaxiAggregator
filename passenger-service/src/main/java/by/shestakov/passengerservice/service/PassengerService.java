package by.shestakov.passengerservice.service;

import by.shestakov.passengerservice.dto.PassengerDto;
import by.shestakov.passengerservice.entity.Passenger;

public interface PassengerService {
    public PassengerDto getById(Long id);

    public PassengerDto create(PassengerDto passengerDto);

    public PassengerDto updateById(PassengerDto passengerDto, Long id);

    public void delete(Long id);

}
