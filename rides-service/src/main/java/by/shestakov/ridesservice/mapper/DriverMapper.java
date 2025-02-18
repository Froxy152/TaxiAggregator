package by.shestakov.ridesservice.mapper;

import by.shestakov.ridesservice.dto.response.DriverResponse;
import by.shestakov.ridesservice.entity.Car;
import by.shestakov.ridesservice.entity.Driver;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;


@Mapper(componentModel = "spring",
    injectionStrategy = InjectionStrategy.CONSTRUCTOR
)
public interface DriverMapper {

    @Mapping(target = "carIds", source = "cars", qualifiedByName = "mapCarsFromIds")
    Driver toEntity(DriverResponse driverResponse);

    @Named("mapCarsFromIds")
    default Set<Car> mapCarsFromIds(Set<Long> carIds) {
        if (carIds == null) {
            return Set.of();
        }
        return carIds.stream().map(id -> {
            Car car = new Car();
            car.setId(id);
            return car;
        }).collect(Collectors.toSet());
    }
}
