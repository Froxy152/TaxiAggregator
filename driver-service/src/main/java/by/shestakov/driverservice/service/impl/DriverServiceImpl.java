package by.shestakov.driverservice.service.impl;

import by.shestakov.driverservice.dto.request.DriverDtoRequest;
import by.shestakov.driverservice.dto.response.DriverDtoResponse;
import by.shestakov.driverservice.entity.Driver;
import by.shestakov.driverservice.mapper.DriverMapper;
import by.shestakov.driverservice.mapper.impl.DriverStructMapper;
import by.shestakov.driverservice.repository.DriverRepository;
import by.shestakov.driverservice.service.DriverService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DriverServiceImpl implements DriverService {

    private final DriverRepository driverRepository;
    private final DriverStructMapper driverMapper;

    @Transactional
    @Override
    public DriverDtoResponse createDriver(DriverDtoRequest driverDtoRequest) {
        System.out.println(driverDtoRequest.toString());
        System.out.println(driverMapper.toEntity(driverDtoRequest).toString());
        Driver newDriver = driverMapper.toEntity(driverDtoRequest);
        driverRepository.save(newDriver);
        return driverMapper.toDto(newDriver);
    }
    @Transactional
    @Override
    public DriverDtoResponse updateDriver(DriverDtoRequest driverDtoRequest, Long id) {
        return null;
    }
    @Transactional
    @Override
    public void deleteDriver(Long id) {

    }
}
