package by.shestakov.driverservice.service;

import by.shestakov.driverservice.dto.request.DriverRequest;
import by.shestakov.driverservice.dto.response.DriverResponse;
import by.shestakov.driverservice.dto.response.PageResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface DriverService {

    public PageResponse<DriverResponse> getAllDrivers(Integer offset, Integer limit);

    public DriverResponse createDriver(DriverRequest driverRequest);

    public DriverResponse updateDriver(DriverRequest driverRequest, Long id);

    public void deleteDriver(Long id);
}
