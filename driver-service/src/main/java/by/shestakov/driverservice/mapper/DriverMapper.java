package by.shestakov.driverservice.mapper;

import by.shestakov.driverservice.dto.request.DriverDtoRequest;
import by.shestakov.driverservice.dto.response.DriverDtoResponse;
import by.shestakov.driverservice.entity.Driver;

public interface DriverMapper {

    public DriverDtoResponse toDto(Driver driver);

    public Driver toEntity(DriverDtoRequest driverDtoRequest);

    public void updateToExists(DriverDtoRequest driverDtoRequest, Driver driver);

}
