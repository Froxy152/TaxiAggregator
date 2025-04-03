package by.shestakov.driverservice.controller.impl;

import static by.shestakov.constant.TestDriverData.DEFAULT_DRIVER_ADDRESS;
import static by.shestakov.constant.TestDriverData.TEST_ID;
import static by.shestakov.constant.TestDriverData.defaultDriverRequest;
import static by.shestakov.constant.TestDriverData.defaultDriverResponse;
import static by.shestakov.constant.TestDriverData.driverUpdateRequest;
import static by.shestakov.constant.TestDriverData.invalidDriverRequest;
import static by.shestakov.constant.TestDriverData.updatedDriver;
import by.shestakov.driverservice.dto.request.DriverRequest;
import by.shestakov.driverservice.dto.request.DriverUpdateRequest;
import by.shestakov.driverservice.dto.response.DriverResponse;
import by.shestakov.driverservice.dto.response.PageResponse;
import by.shestakov.driverservice.exception.GlobalExceptionHandler;
import by.shestakov.driverservice.exception.driver.DriverAlreadyExistsException;
import by.shestakov.driverservice.exception.driver.DriverNotFoundException;
import by.shestakov.driverservice.service.DriverService;
import by.shestakov.driverservice.util.ExceptionMessages;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ExtendWith(MockitoExtension.class)
class DriverControllerImplTest {

    @InjectMocks
    private DriverControllerImpl driverController;

    @Mock
    private DriverService driverService;

    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(driverController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    @Test
    void getAll() throws Exception {
        int offset = 0;
        int limit = 2;
        DriverResponse response = defaultDriverResponse();

        when(driverService.getAllDrivers(offset, limit))
                .thenReturn(new PageResponse<>(offset, limit, 1, 1, "", List.of(response)));

        mockMvc.perform(get(DEFAULT_DRIVER_ADDRESS)
                        .param("offset", String.valueOf(offset))
                        .param("limit", String.valueOf(limit))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        verify(driverService).getAllDrivers(offset, limit);
    }

    @Test
    void getById() throws Exception {
        Long id = TEST_ID;
        DriverResponse response = defaultDriverResponse();

        when(driverService.getById(id)).thenReturn(response);

        mockMvc.perform(get(DEFAULT_DRIVER_ADDRESS + "/{id}", id))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(response.id()))
                .andExpect(jsonPath("$.name").value(response.name()));

        verify(driverService).getById(id);
    }

    @Test
    void getById_DriverNotFound_ThrowException() throws Exception {
        Long id = TEST_ID;

        when(driverService.getById(id))
                .thenThrow(new DriverNotFoundException(
                        ExceptionMessages.NOT_FOUND_MESSAGE.formatted("driver", id)));

        mockMvc.perform(get(DEFAULT_DRIVER_ADDRESS + "/{id}", id))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(driverService).getById(id);
    }

    @Test
    void create() throws Exception {
        DriverRequest request = defaultDriverRequest();
        DriverResponse response = defaultDriverResponse();

        when(driverService.createDriver(request)).thenReturn(response);

        mockMvc.perform(post(DEFAULT_DRIVER_ADDRESS)
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(response.id()))
                .andExpect(jsonPath("$.name").value(response.name()));

        verify(driverService).createDriver(request);
    }

    @Test
    void create_BadRequest_ThrowException() throws Exception {
        DriverRequest invalidRequest = invalidDriverRequest();

        mockMvc.perform(post(DEFAULT_DRIVER_ADDRESS)
                        .content(objectMapper.writeValueAsString(invalidRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void create_DriverExistsByEmailOrPhoneNumber_ThrowException() throws Exception {
        DriverRequest request = defaultDriverRequest();

        when(driverService.createDriver(request)).thenThrow(new DriverAlreadyExistsException(
                ExceptionMessages.CONFLICT_MESSAGE.formatted("driver")));

        mockMvc.perform(post(DEFAULT_DRIVER_ADDRESS)
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict());
    }

    @Test
    void updateDriver() throws Exception {
        DriverUpdateRequest request = driverUpdateRequest();
        DriverResponse response = updatedDriver();
        Long id = TEST_ID;
        when(driverService.updateDriver(request, id)).thenReturn(response);

        mockMvc.perform(put(DEFAULT_DRIVER_ADDRESS + "/{id}", id)
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.phoneNumber").value(response.phoneNumber()))
                .andExpect(jsonPath("$.email").value(response.email()));

        verify(driverService).updateDriver(request, id);
    }

    @Test
    void updateDriver_DriverExists_ThrowException() throws Exception {
        DriverUpdateRequest request = driverUpdateRequest();
        Long id = TEST_ID;

        when(driverService.updateDriver(request, id)).thenThrow(new DriverAlreadyExistsException(
                ExceptionMessages.CONFLICT_MESSAGE.formatted("driver")));

        mockMvc.perform(put(DEFAULT_DRIVER_ADDRESS + "/{id}", id)
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void updateDriver_DriverNotFound_ThrowException() throws Exception {
        DriverUpdateRequest request = driverUpdateRequest();
        Long id = TEST_ID;

        when(driverService.updateDriver(request, id)).thenThrow(new DriverNotFoundException(
                ExceptionMessages.NOT_FOUND_MESSAGE.formatted("driver", id)));

        mockMvc.perform(put(DEFAULT_DRIVER_ADDRESS + "/{id}", id)
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void deleteDriver() throws Exception {
        Long id = TEST_ID;

        mockMvc.perform(delete(DEFAULT_DRIVER_ADDRESS + "/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

    }

    @Test
    void deleteDriver_DriverNotFound_ThrowException() throws Exception {
        Long id = TEST_ID;

        doThrow(new DriverNotFoundException(ExceptionMessages.NOT_FOUND_MESSAGE.formatted("driver", id)))
                .when(driverService).deleteDriver(id);

        mockMvc.perform(delete(DEFAULT_DRIVER_ADDRESS + "/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }
}