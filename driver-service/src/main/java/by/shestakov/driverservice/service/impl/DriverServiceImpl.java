package by.shestakov.driverservice.service.impl;

import by.shestakov.driverservice.dto.request.DriverRequest;
import by.shestakov.driverservice.dto.response.DriverResponse;
import by.shestakov.driverservice.entity.Driver;
import by.shestakov.driverservice.exception.driver.DriverAlreadyExistsException;
import by.shestakov.driverservice.exception.driver.DriverNotFoundException;
import by.shestakov.driverservice.exception.driver.DriverWasDeletedException;
import by.shestakov.driverservice.mapper.DriverMapper;
import by.shestakov.driverservice.repository.DriverRepository;
import by.shestakov.driverservice.service.DriverService;
import by.shestakov.driverservice.util.ExceptionMessages;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DriverServiceImpl implements DriverService {

    private final DriverRepository driverRepository;
    private final DriverMapper driverMapper;

    public List<DriverResponse> getAllDrivers(){
        return driverRepository.findAll().stream().map(driverMapper::toDto).collect(Collectors.toList());
    }
    @Transactional
    @Override
    public DriverResponse createDriver(DriverRequest driverRequest) {
        if(driverRepository.existsByEmailOrPhoneNumber(driverRequest.email(), driverRequest.phoneNumber())){
            throw new DriverAlreadyExistsException(String.format(ExceptionMessages.CONFLICT_MESSAGE,"driver"));
        }
        Driver newDriver = driverMapper.toEntity(driverRequest);
        newDriver.setIsDeleted(false);
        driverRepository.save(newDriver);
        return driverMapper.toDto(newDriver);
    }
    @Transactional
    @Override
    public DriverResponse updateDriver(DriverRequest driverRequest, Long id) {
        Driver existsDriver = driverRepository.findById(id).orElseThrow(() -> new DriverNotFoundException(String.format(ExceptionMessages.NOT_FOUND_MESSAGE,"driver",id)));
        if(existsDriver.getIsDeleted()){
            throw new DriverWasDeletedException(String.format(ExceptionMessages.BAD_REQUEST_MESSAGE,"driver"));
        }else if(driverRepository.existsByEmailOrPhoneNumber(existsDriver.getEmail(), existsDriver.getPhoneNumber())){
            throw new DriverAlreadyExistsException(String.format(ExceptionMessages.CONFLICT_MESSAGE,"driver"));
        }
        driverMapper.updateToExists(driverRequest, existsDriver);
        driverRepository.save(existsDriver);
        return driverMapper.toDto(existsDriver);
    }
    @Transactional
    @Override
    public void deleteDriver(Long id) {
        Driver existsDriver = driverRepository.findById(id).orElseThrow(() -> new DriverNotFoundException(""));
        if(existsDriver.getIsDeleted()){
            throw new DriverWasDeletedException(String.format(ExceptionMessages.BAD_REQUEST_MESSAGE,"driver"));
        }
        existsDriver.setIsDeleted(true);
        driverRepository.save(existsDriver);
    }
}
