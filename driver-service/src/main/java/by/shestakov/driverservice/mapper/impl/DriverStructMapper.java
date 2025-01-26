package by.shestakov.driverservice.mapper.impl;

import by.shestakov.driverservice.dto.request.DriverDtoRequest;
import by.shestakov.driverservice.dto.response.DriverDtoResponse;
import by.shestakov.driverservice.entity.Driver;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface DriverStructMapper {
    public DriverDtoResponse toDto(Driver driver);
    DriverStructMapper INSTANCE = Mappers.getMapper(DriverStructMapper.class);

    public Driver toEntity(DriverDtoRequest driverDtoRequest);
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    public void updateToExists(DriverDtoRequest driverDtoRequest,@MappingTarget Driver driver);
}
