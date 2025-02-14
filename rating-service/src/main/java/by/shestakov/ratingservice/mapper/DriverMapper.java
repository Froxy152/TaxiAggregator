package by.shestakov.ratingservice.mapper;

import by.shestakov.ratingservice.dto.response.DriverResponse;
import by.shestakov.ratingservice.entity.Driver;
import org.mapstruct.*;

@Mapper(componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR, uses = CarMapper.class)
public interface DriverMapper {
    @Mapping(target = "cars", source = "cars")
    DriverResponse toDto(Driver driver);

    @Mapping(target = "cars", ignore = true)
    Driver toEntity(DriverResponse driverRequest);
}
