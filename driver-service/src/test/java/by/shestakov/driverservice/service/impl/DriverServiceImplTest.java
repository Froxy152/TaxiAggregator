package by.shestakov.driverservice.service.impl;

import static by.shestakov.constant.TestDriverData.TEST_ID;
import static by.shestakov.constant.TestDriverData.defaultDriver;
import static by.shestakov.constant.TestDriverData.defaultDriverRequest;
import static by.shestakov.constant.TestDriverData.defaultDriverResponse;
import static by.shestakov.constant.TestDriverData.driverUpdateRequest;
import static by.shestakov.constant.TestDriverData.updatedDriver;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
import by.shestakov.driverservice.util.ExceptionMessages;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

@ExtendWith(MockitoExtension.class)
class DriverServiceImplTest {


    @Mock
    private DriverRepository driverRepository;

    @Mock
    private DriverMapper driverMapper;

    @Mock
    private PageMapper pageMapper;

    @InjectMocks
    private DriverServiceImpl driverService;

    @Test
    void getAllDrivers() {
        int offset = 0;
        int limit = 2;

        List<Driver> drivers = List.of(defaultDriver());
        Page<Driver> driverPage = new PageImpl<>(drivers, PageRequest.of(offset, limit), drivers.size());
        List<DriverResponse> driverResponses = List.of(defaultDriverResponse());
        Page<DriverResponse> responsePage =
            new PageImpl<>(driverResponses, PageRequest.of(offset, limit), driverResponses.size());
        PageResponse<DriverResponse> expectedResponse =
            new PageResponse<>(offset, limit, 1, driverResponses.size(), "", driverResponses);

        when(driverRepository.findAllByIsDeletedFalse(PageRequest.of(offset, limit))).thenReturn(driverPage);
        when(driverMapper.toDto(any(Driver.class))).thenReturn(defaultDriverResponse());
        when(pageMapper.toDto(responsePage)).thenReturn(expectedResponse);

        PageResponse<DriverResponse> response = driverService.getAllDrivers(offset, limit);

        assertNotNull(response);
        assertEquals(expectedResponse, response);

        verify(driverRepository).findAllByIsDeletedFalse(PageRequest.of(offset, limit));
        verify(driverMapper, times(drivers.size())).toDto(any(Driver.class));
        verify(pageMapper).toDto(responsePage);
    }

    @Test
    void getById_ReturnsValidDriverResponse() {
        Long id = TEST_ID;

        DriverResponse expectedResponse = defaultDriverResponse();
        Driver driver = defaultDriver();

        when(driverRepository.findByIdAndIsDeletedFalse(id)).thenReturn(Optional.of(driver));
        when(driverMapper.toDto(driver)).thenReturn(expectedResponse);

        DriverResponse response = driverService.getById(id);

        assertNotNull(response);
        assertEquals(expectedResponse.id(), response.id());
        assertEquals(expectedResponse.name(), response.name());
        assertEquals(expectedResponse.lastName(), response.lastName());
        assertEquals(expectedResponse.email(), response.email());
        assertEquals(expectedResponse.phoneNumber(), response.phoneNumber());
        assertEquals(expectedResponse.gender(), response.gender());
        assertEquals(expectedResponse.rating(), response.rating());
        assertEquals(expectedResponse.cars(), response.cars());

        verify(driverRepository).findByIdAndIsDeletedFalse(id);
        verify(driverMapper).toDto(driver);
    }

    @Test
    void getById_NotFoundDriver_ThrowException() {
        Long id = TEST_ID;

        when(driverRepository.findByIdAndIsDeletedFalse(id)).thenReturn(Optional.empty());

        DriverNotFoundException exception = assertThrows(
            DriverNotFoundException.class,
            () -> driverService.getById(id));

        assertEquals(ExceptionMessages.NOT_FOUND_MESSAGE.formatted("driver", id), exception.getMessage());

        verify(driverRepository).findByIdAndIsDeletedFalse(id);
    }

    @Test
    void createDriver_ReturnsValidResponse() {
        DriverRequest request = defaultDriverRequest();
        Driver driver = defaultDriver();
        DriverResponse expectedResponse = defaultDriverResponse();

        when(driverRepository.existsByEmailOrPhoneNumber(request.email(), request.phoneNumber())).thenReturn(false);
        when(driverMapper.toEntity(request)).thenReturn(driver);
        when(driverRepository.save(driver)).thenReturn(driver);
        when(driverMapper.toDto(driver)).thenReturn(expectedResponse);

        DriverResponse response = driverService.createDriver(request);

        assertNotNull(response);
        assertEquals(expectedResponse.id(), response.id());
        assertEquals(expectedResponse.name(), response.name());
        assertEquals(expectedResponse.lastName(), response.lastName());
        assertEquals(expectedResponse.email(), response.email());
        assertEquals(expectedResponse.phoneNumber(), response.phoneNumber());
        assertEquals(expectedResponse.gender(), response.gender());
        assertEquals(expectedResponse.rating(), response.rating());
        assertEquals(expectedResponse.cars(), response.cars());
        assertEquals(BigDecimal.valueOf(0.0), driver.getRating());
        assertFalse(driver.getIsDeleted());


        verify(driverRepository).existsByEmailOrPhoneNumber(request.email(), request.phoneNumber());
        verify(driverMapper).toEntity(request);
        verify(driverRepository).save(driver);
        verify(driverMapper).toDto(driver);
    }

    @Test
    void createDriver_DriverExists_ThrowException() {
        DriverRequest request = defaultDriverRequest();

        when(driverRepository.existsByEmailOrPhoneNumber(request.email(), request.phoneNumber())).thenReturn(true);

        DriverAlreadyExistsException exception = assertThrows(
            DriverAlreadyExistsException.class,
            () -> driverService.createDriver(request)
        );

        assertEquals(ExceptionMessages.CONFLICT_MESSAGE.formatted("driver"), exception.getMessage());

        verify(driverRepository).existsByEmailOrPhoneNumber(request.email(), request.phoneNumber());
    }

    @Test
    void updateDriver_ReturnsValidResponse() {
        Long id = TEST_ID;
        Driver driver = defaultDriver();
        DriverUpdateRequest request = driverUpdateRequest();
        DriverResponse expectedResponse = updatedDriver();

        when(driverRepository.findByIdAndIsDeletedFalse(id)).thenReturn(Optional.of(driver));
        when(driverRepository.existsByEmailOrPhoneNumber(request.email(), request.phoneNumber()))
            .thenReturn(false);
        when(driverMapper.toDto(driver)).thenReturn(expectedResponse);

        DriverResponse response = driverService.updateDriver(request, id);

        assertNotNull(response);
        assertEquals(expectedResponse, response);

        verify(driverRepository).findByIdAndIsDeletedFalse(id);
        verify(driverRepository).existsByEmailOrPhoneNumber(request.email(), request.phoneNumber());
        verify(driverMapper).updateToExists(request, driver);
        verify(driverRepository).save(driver);
    }

    @Test
    void updateDriver_NotFoundDriver_ThrowException() {
        Long id = TEST_ID;
        DriverUpdateRequest request = driverUpdateRequest();

        when(driverRepository.findByIdAndIsDeletedFalse(id)).thenReturn(Optional.empty());

        DriverNotFoundException exception = assertThrows(
            DriverNotFoundException.class,
            () -> driverService.updateDriver(request, id));

        assertEquals(ExceptionMessages.NOT_FOUND_MESSAGE.formatted("driver", id), exception.getMessage());

        verify(driverRepository).findByIdAndIsDeletedFalse(id);
    }

    @Test
    void updateDriver_DriverExists_ThrowException() {
        Long id = TEST_ID;
        DriverUpdateRequest request = driverUpdateRequest();
        Driver driver = defaultDriver();

        when(driverRepository.findByIdAndIsDeletedFalse(id)).thenReturn(Optional.of(driver));
        when(driverRepository.existsByEmailOrPhoneNumber(request.email(), request.phoneNumber())).thenReturn(true);

        DriverAlreadyExistsException exception = assertThrows(
            DriverAlreadyExistsException.class,
            () -> driverService.updateDriver(request, id)
        );

        assertEquals(ExceptionMessages.CONFLICT_MESSAGE.formatted("driver"), exception.getMessage());

        verify(driverRepository).findByIdAndIsDeletedFalse(id);
        verify(driverRepository).existsByEmailOrPhoneNumber(request.email(), request.phoneNumber());
    }

    @Test
    void deleteDriver_ReturnsValidDeleted() {
        Long id = TEST_ID;
        Driver driver = defaultDriver();

        when(driverRepository.findByIdAndIsDeletedFalse(id)).thenReturn(Optional.of(driver));

        driverService.deleteDriver(id);

        assertTrue(driver.getIsDeleted());

        verify(driverRepository).findByIdAndIsDeletedFalse(id);
        verify(driverRepository).save(driver);
    }

    @Test
    void deleteDriver_DriverNotFound_ThrowException() {
        Long id = TEST_ID;

        when(driverRepository.findByIdAndIsDeletedFalse(id)).thenReturn(Optional.empty());

        DriverNotFoundException exception = assertThrows(
            DriverNotFoundException.class,
            () -> driverService.deleteDriver(id)
        );

        assertEquals(ExceptionMessages.NOT_FOUND_MESSAGE.formatted("driver", id), exception.getMessage());
    }
}