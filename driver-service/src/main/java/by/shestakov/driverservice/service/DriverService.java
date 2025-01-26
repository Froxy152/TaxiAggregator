package by.shestakov.driverservice.service;

import by.shestakov.driverservice.dto.request.DriverDtoRequest;
import by.shestakov.driverservice.dto.response.DriverDtoResponse;

public interface DriverService {
    public DriverDtoResponse createDriver(DriverDtoRequest driverDtoRequest);

    public DriverDtoResponse updateDriver(DriverDtoRequest driverDtoRequest, Long id);

    public void deleteDriver(Long id);
}
