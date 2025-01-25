package by.shestakov.driverservice.mapper.impl;

import by.shestakov.driverservice.dto.DriverDto;
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
    public DriverDto toDto(Driver driver) {
        return modelMapper.map(driver, DriverDto.class);
    }

    @Override
    public Driver toEntity(DriverDto driverDto) {
        return modelMapper.map(driverDto,Driver.class);
    }

    @Override
    public void updateToExists(DriverDto driverDto, Driver driver) {
        modelMapper.map(driverDto, driver);
    }
}
