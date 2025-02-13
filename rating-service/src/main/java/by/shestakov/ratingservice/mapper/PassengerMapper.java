package by.shestakov.ratingservice.mapper;

import by.shestakov.ratingservice.dto.response.PassengerResponse;
import by.shestakov.ratingservice.entity.Passenger;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface PassengerMapper {

    Passenger toEntity(PassengerResponse passengerResponse);

}
