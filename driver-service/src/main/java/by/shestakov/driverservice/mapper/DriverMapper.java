package by.shestakov.driverservice.mapper;

import by.shestakov.driverservice.dto.DriverDto;
import by.shestakov.driverservice.entity.Driver;

public interface DriverMapper {

    public DriverDto toDto(Driver driver);

    public Driver toEntity(DriverDto driverDto);

    public void updateToExists(DriverDto driverDto, Driver driver);

}
