package by.shestakov.driverservice.service.impl;

import by.shestakov.driverservice.dto.DriverDto;
import by.shestakov.driverservice.mapper.DriverMapper;
import by.shestakov.driverservice.repository.DriverRepository;
import by.shestakov.driverservice.service.DriverService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DriverServiceImpl implements DriverService {

    private final DriverRepository driverRepository;
    private final DriverMapper driverMapper;

    @Transactional
    @Override
    public DriverDto createDriver(DriverDto driverDto) {
        System.out.println(driverDto.toString());
        System.out.println(driverMapper.toEntity(driverDto).toString());
        driverRepository.save(driverMapper.toEntity(driverDto));
        return driverDto;
    }
    @Transactional
    @Override
    public DriverDto updateDriver(DriverDto driverDto, Long id) {
        return null;
    }
    @Transactional
    @Override
    public void deleteDriver(Long id) {

    }
}
