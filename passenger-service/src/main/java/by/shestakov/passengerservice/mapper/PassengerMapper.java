package by.shestakov.passengerservice.mapper;

import by.shestakov.passengerservice.dto.PassengerDto;
import by.shestakov.passengerservice.entity.Passenger;

public interface PassengerMapper {
    public PassengerDto toDto(Passenger passenger);

    public Passenger toEntity(PassengerDto passengerDto);

    public void toUpdateExists(PassengerDto passengerDto, Passenger passenger);

}
