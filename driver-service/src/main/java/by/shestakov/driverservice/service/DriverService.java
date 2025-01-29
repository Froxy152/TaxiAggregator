package by.shestakov.driverservice.service;

import by.shestakov.driverservice.dto.request.DriverRequest;
import by.shestakov.driverservice.dto.response.DriverResponse;
import by.shestakov.driverservice.entity.Driver;

import java.util.List;

public interface DriverService {

    public List<DriverResponse> getAllDrivers();
    public DriverResponse createDriver(DriverRequest driverRequest);

    public DriverResponse updateDriver(DriverRequest driverRequest, Long id);

    public void deleteDriver(Long id);
}
