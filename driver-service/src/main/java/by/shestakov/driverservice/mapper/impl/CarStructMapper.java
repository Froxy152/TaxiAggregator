package by.shestakov.driverservice.mapper.impl;

import by.shestakov.driverservice.dto.request.CarDtoRequest;
import by.shestakov.driverservice.dto.response.CarDtoResponse;
import by.shestakov.driverservice.entity.Car;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface CarStructMapper {
    public CarDtoResponse toDto(Car car);
    CarStructMapper INSTANCE = Mappers.getMapper(CarStructMapper.class);
    public Car toEntity(CarDtoRequest carDtoRequest);
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    public void updateToExists(CarDtoRequest carDtoRequest,@MappingTarget Car car);
}
