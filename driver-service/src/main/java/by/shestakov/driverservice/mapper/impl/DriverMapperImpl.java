package by.shestakov.driverservice.mapper.impl;

import by.shestakov.driverservice.dto.request.DriverDtoRequest;
import by.shestakov.driverservice.dto.response.DriverDtoResponse;
import by.shestakov.driverservice.entity.Driver;
import by.shestakov.driverservice.mapper.DriverMapper;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DriverMapperImpl implements DriverMapper {
    private final ModelMapper modelMapper;

    @Override
    public DriverDtoResponse toDto(Driver driver) {
        return modelMapper.map(driver, DriverDtoResponse.class);
    }

    @Override
    public Driver toEntity(DriverDtoRequest driverDtoRequest) {
        return modelMapper.map(driverDtoRequest,Driver.class);
    }

    @Override
    public void updateToExists(DriverDtoRequest driverDtoRequest, Driver driver) {
        modelMapper.map(driverDtoRequest, driver);
    }
}
