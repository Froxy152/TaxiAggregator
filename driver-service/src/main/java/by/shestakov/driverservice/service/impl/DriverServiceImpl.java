package by.shestakov.driverservice.service.impl;

import by.shestakov.driverservice.dto.request.DriverRequest;
import by.shestakov.driverservice.dto.request.DriverUpdateRequest;
import by.shestakov.driverservice.dto.response.DriverResponse;
import by.shestakov.driverservice.dto.response.PageResponse;
import by.shestakov.driverservice.entity.Driver;
import by.shestakov.driverservice.exception.driver.DriverAlreadyExistsException;
import by.shestakov.driverservice.exception.driver.DriverNotFoundException;
import by.shestakov.driverservice.mapper.DriverMapper;
import by.shestakov.driverservice.mapper.PageMapper;
import by.shestakov.driverservice.repository.DriverRepository;
import by.shestakov.driverservice.service.DriverService;
import by.shestakov.driverservice.util.ExceptionMessages;
import java.math.BigDecimal;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class DriverServiceImpl implements DriverService {

    private final DriverRepository driverRepository;

    private final DriverMapper driverMapper;

    private final PageMapper pageMapper;

    @Override
    public PageResponse<DriverResponse> getAllDrivers(Integer offset, Integer limit) {
        log.debug("Entering in getAllDrivers method. Offset: {}, Limit: {}", offset, limit);
        Page<DriverResponse> driverPageDto = driverRepository
                .findAllByIsDeletedFalse(PageRequest.of(offset, limit))
                .map(driverMapper::toDto);

        log.info("getAllDrivers: Returns all drivers. Drivers: {}", driverPageDto);
        return pageMapper.toDto(driverPageDto);
    }

    @Override
    public DriverResponse getById(Long id) {
        log.debug("Entering in getById method. DriverId: {}", id);
        Driver existsDriver = driverRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new DriverNotFoundException(
                        ExceptionMessages.NOT_FOUND_MESSAGE.formatted("driver", id)));

        log.info("getById: Driver found by id. Driver: {}", existsDriver);
        return driverMapper.toDto(existsDriver);
    }

    @Transactional
    @Override
    public DriverResponse createDriver(DriverRequest driverRequest) {
        log.debug("Entering in createDriver. DriverRequest: {}", driverRequest);
        if (driverRepository.existsByEmailOrPhoneNumber(driverRequest.email(), driverRequest.phoneNumber())) {
            log.error("createDriver: Driver already exists with Email or Phone number. Email: {}, Phone number: {}",
                    driverRequest.email(), driverRequest.phoneNumber());
            throw new DriverAlreadyExistsException(
                    ExceptionMessages.CONFLICT_MESSAGE.formatted("driver"));
        }

        Driver newDriver = driverMapper.toEntity(driverRequest);
        newDriver.setRating(BigDecimal.valueOf(0.0));
        newDriver.setIsDeleted(false);
        driverRepository.save(newDriver);

        log.info("createDriver: Driver successfully created. Driver: {}", newDriver);
        return driverMapper.toDto(newDriver);
    }

    @Transactional
    @Override
    public DriverResponse updateDriver(DriverUpdateRequest driverUpdateRequest, Long id) {
        log.debug("Entering in updateDriver method. DriverUpdateRequest: {}, DriverId: {}", driverUpdateRequest, id);
        Driver existsDriver = driverRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new DriverNotFoundException(
                        ExceptionMessages.NOT_FOUND_MESSAGE.formatted("driver", id)));

        if (driverRepository.existsByEmailOrPhoneNumber(driverUpdateRequest.email(),
                driverUpdateRequest.phoneNumber())) {
            log.error("updateDriver: Driver already exists with Email or Phone number. Email: {}, Phone number: {}",
                    driverUpdateRequest.email(), driverUpdateRequest.phoneNumber());
            throw new DriverAlreadyExistsException(
                    ExceptionMessages.CONFLICT_MESSAGE.formatted("driver"));
        }

        driverMapper.updateToExists(driverUpdateRequest, existsDriver);
        driverRepository.save(existsDriver);

        log.info("updateDriver: Driver successfully updated. Driver: {}", existsDriver);
        return driverMapper.toDto(existsDriver);
    }

    @Transactional
    @Override
    public void deleteDriver(Long id) {
        log.debug("Entering in deleteDriver method. DriverId: {}", id);
        Driver existsDriver = driverRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new DriverNotFoundException(
                        ExceptionMessages.NOT_FOUND_MESSAGE.formatted("driver", id)));
        existsDriver.setIsDeleted(true);

        log.info("deleteDriver: Driver successfully deleted. Driver: {}", existsDriver);
        driverRepository.save(existsDriver);
    }

}
