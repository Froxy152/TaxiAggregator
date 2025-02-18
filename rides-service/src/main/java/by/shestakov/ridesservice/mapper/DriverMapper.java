package by.shestakov.ridesservice.mapper;

import by.shestakov.ridesservice.dto.response.DriverResponse;
import by.shestakov.ridesservice.entity.Driver;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring",
    injectionStrategy = InjectionStrategy.CONSTRUCTOR
)
public interface DriverMapper {

    Driver toEntity(DriverResponse driverResponse);
}
