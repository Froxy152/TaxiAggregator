package by.shestakov.ratingservice.mapper;

import by.shestakov.ratingservice.dto.response.CarResponse;
import by.shestakov.ratingservice.entity.Car;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface CarMapper {

    @Mapping(target = "driverId", ignore = true)
    Car toEntity(CarResponse carRequest);
}
