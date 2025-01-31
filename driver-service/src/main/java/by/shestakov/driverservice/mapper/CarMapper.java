package by.shestakov.driverservice.mapper;

import by.shestakov.driverservice.dto.request.CarRequest;
import by.shestakov.driverservice.dto.response.CarResponse;
import by.shestakov.driverservice.entity.Car;
import org.mapstruct.*;

@Mapper(componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface CarMapper {
    @Mapping(target = "driverId", source = "id")
    public CarResponse toDto(Car car);

    @Mapping(target = "driverId", ignore = true)
    public Car toEntity(CarRequest carRequest);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "driverId", ignore = true)
    public void updateToExists(CarRequest carRequest, @MappingTarget Car car);


}
