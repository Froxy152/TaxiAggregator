package by.shestakov.driverservice.mapper;

import by.shestakov.driverservice.dto.request.CarRequest;
import by.shestakov.driverservice.dto.request.CarUpdateRequest;
import by.shestakov.driverservice.dto.response.CarResponse;
import by.shestakov.driverservice.entity.Car;
import org.mapstruct.BeanMapping;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface CarMapper {
    @Mapping(target = "driverId", source = "id")
    CarResponse toDto(Car car);

    @Mapping(target = "driver", ignore = true)
    Car toEntity(CarRequest carRequest);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "driver", ignore = true)
    void updateToExists(CarUpdateRequest carUpdateRequest, @MappingTarget Car car);


}
