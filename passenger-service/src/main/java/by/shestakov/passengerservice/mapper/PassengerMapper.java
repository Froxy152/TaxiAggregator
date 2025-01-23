package by.shestakov.passengerservice.mapper;

import by.shestakov.passengerservice.dto.PassengerDto;
import by.shestakov.passengerservice.entity.Passenger;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class PassengerMapper {

    private final ModelMapper modelMapper;

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
