package by.shestakov.driverservice.service;

import by.shestakov.driverservice.dto.request.DriverRequest;
import by.shestakov.driverservice.dto.request.UpdateDriverRequest;
import by.shestakov.driverservice.dto.response.DriverResponse;
import by.shestakov.driverservice.dto.response.PageResponse;

public interface DriverService {

    PageResponse<DriverResponse> getAllDrivers(Integer offset, Integer limit);

    DriverResponse createDriver(DriverRequest driverRequest);

    DriverResponse updateDriver(UpdateDriverRequest driverRequest, Long id);

    void deleteDriver(Long id);
}
