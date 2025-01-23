package by.shestakov.passenger_service.mapper;

import by.shestakov.passenger_service.dto.PassengerDto;
import by.shestakov.passenger_service.entity.Passenger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PassengerMapper {

    @Autowired
    private  ModelMapper modelMapper;

    public PassengerDto toDto(Passenger passenger){
        return modelMapper.map(passenger, PassengerDto.class);
    }

    public Passenger toEntity(PassengerDto passengerDto){
        return modelMapper.map(passengerDto, Passenger.class);
    }

    public void toUpdateExists(PassengerDto passengerDto, Passenger passenger){
        modelMapper.map(passengerDto,passenger);
    }


}
