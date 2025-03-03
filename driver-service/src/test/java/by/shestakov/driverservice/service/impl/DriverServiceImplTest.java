package by.shestakov.driverservice.service.impl;

import static by.shestakov.constant.TestDriverData.TEST_ID;
import static by.shestakov.constant.TestDriverData.defaultDriver;
import static by.shestakov.constant.TestDriverData.defaultDriverRequest;
import static by.shestakov.constant.TestDriverData.defaultDriverResponse;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import by.shestakov.driverservice.dto.request.DriverRequest;
import by.shestakov.driverservice.dto.response.DriverResponse;
import by.shestakov.driverservice.entity.Driver;
import by.shestakov.driverservice.exception.driver.DriverNotFoundException;
import by.shestakov.driverservice.mapper.DriverMapper;
import by.shestakov.driverservice.repository.DriverRepository;
import jakarta.persistence.Id;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class DriverServiceImplTest {


    @Mock
    private DriverRepository driverRepository;

    @Mock
    private DriverMapper driverMapper;

    @InjectMocks
    private DriverServiceImpl driverService;

    @BeforeEach
    void setUp() {

    }

    @Test
    void getAllDrivers() {


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

        verify(driverRepository).findByIdAndIsDeletedFalse(id);
    }

    @Test
    void createDriver() {
    }

    @Test
    void updateDriver() {
    }

    @Test
    void deleteDriver() {
    }
}