package by.shestakov.ridesservice.mapper;

import by.shestakov.ridesservice.dto.response.PassengerResponse;
import by.shestakov.ridesservice.entity.Passenger;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface PassengerMapper {

    Passenger toEntity(PassengerResponse passengerResponse);
}
