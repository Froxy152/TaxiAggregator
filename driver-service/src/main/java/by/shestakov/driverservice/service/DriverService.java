package by.shestakov.driverservice.service;

import by.shestakov.driverservice.dto.DriverDto;
import by.shestakov.driverservice.entity.Driver;

public interface DriverService {
    public DriverDto createDriver(DriverDto driverDto);

    public DriverDto updateDriver(DriverDto driverDto, Long id);

    public void deleteDriver(Long id);
}
