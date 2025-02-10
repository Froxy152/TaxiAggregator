package by.shestakov.passengerservice.mapper;

import by.shestakov.passengerservice.dto.request.PassengerRequest;
import by.shestakov.passengerservice.dto.request.UpdatePassengerRequest;
import by.shestakov.passengerservice.dto.response.PassengerResponse;
import by.shestakov.passengerservice.entity.Passenger;
import org.mapstruct.BeanMapping;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface PassengerMapper {

    PassengerResponse toDto(Passenger passenger);

    Passenger toEntity(PassengerRequest passengerRequest);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void update(UpdatePassengerRequest updatePassengerRequest, @MappingTarget Passenger passenger);

}
