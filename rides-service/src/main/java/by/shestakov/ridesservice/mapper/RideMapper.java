package by.shestakov.ridesservice.mapper;

import by.shestakov.ridesservice.dto.request.RideRequest;
import by.shestakov.ridesservice.dto.response.RideResponse;
import by.shestakov.ridesservice.entity.Ride;
import org.mapstruct.*;

@Mapper(componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR
)
public interface RideMapper {

    RideResponse toDto(Ride ride);

    @Mapping(target = "distance", ignore = true)
    @Mapping(target = "time", ignore = true)
    @Mapping(target = "duringRide", ignore = true)
    @Mapping(target = "price", ignore = true)
    Ride toEntity(RideRequest rideRequest);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateExists(RideRequest rideRequest, @MappingTarget Ride ride);
}
