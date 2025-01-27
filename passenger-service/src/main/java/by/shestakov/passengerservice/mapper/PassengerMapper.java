package by.shestakov.passengerservice.mapper;

import by.shestakov.passengerservice.dto.request.PassengerRequest;
import by.shestakov.passengerservice.dto.response.PassengerResponse;
import by.shestakov.passengerservice.entity.Passenger;
import org.mapstruct.*;

@Mapper(componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface PassengerMapper {

    public PassengerResponse toDto(Passenger passenger);

    public Passenger toEntity(PassengerRequest passengerRequest);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    public void toUpdateExists(PassengerRequest passengerRequest, @MappingTarget Passenger passenger);

}
