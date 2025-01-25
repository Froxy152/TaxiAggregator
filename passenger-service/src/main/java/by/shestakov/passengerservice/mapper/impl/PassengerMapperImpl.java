package by.shestakov.passengerservice.mapper.impl;

import by.shestakov.passengerservice.dto.request.PassengerDtoRequest;
import by.shestakov.passengerservice.dto.response.PassengerDtoResponse;
import by.shestakov.passengerservice.entity.Passenger;
import by.shestakov.passengerservice.mapper.PassengerMapper;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class PassengerMapperImpl implements PassengerMapper {

    private final ModelMapper modelMapper;

    public PassengerDtoResponse toDto(Passenger passenger){
        return modelMapper.map(passenger, PassengerDtoResponse.class);
    }

    public Passenger toEntity(PassengerDtoRequest passengerDtoRequest){
        return modelMapper.map(passengerDtoRequest, Passenger.class);
    }

    public void toUpdateExists(PassengerDtoRequest passengerDtoRequest, Passenger passenger){
        modelMapper.map(passengerDtoRequest,passenger);
    }


}
