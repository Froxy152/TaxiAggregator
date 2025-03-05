package by.shestakov.driverservice.controller.impl;

import static by.shestakov.constant.TestCarData.TEST_CAR_ID;
import static by.shestakov.constant.TestCarData.TEST_DRIVER_ID;
import static by.shestakov.constant.TestCarData.defaultCarRequest;
import static by.shestakov.constant.TestCarData.defaultCarResponse;
import static by.shestakov.constant.TestCarData.updateRequest;
import static by.shestakov.constant.TestCarData.updatedResponse;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import by.shestakov.constant.TestCarData;
import by.shestakov.driverservice.dto.request.CarRequest;
import by.shestakov.driverservice.dto.request.CarUpdateRequest;
import by.shestakov.driverservice.dto.response.CarResponse;
import by.shestakov.driverservice.dto.response.PageResponse;
import by.shestakov.driverservice.exception.GlobalExceptionHandler;
import by.shestakov.driverservice.exception.car.CarNotFoundException;
import by.shestakov.driverservice.exception.car.CarNumberAlreadyException;
import by.shestakov.driverservice.exception.driver.DriverNotFoundException;
import by.shestakov.driverservice.service.CarService;
import by.shestakov.driverservice.util.ExceptionMessages;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ExtendWith(MockitoExtension.class)
class CarControllerImplTest {

    @InjectMocks
    private CarControllerImpl carController;

    @Mock
    private CarService carService;

    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(carController)
            .setControllerAdvice(new GlobalExceptionHandler())
            .build();
    }

    @Test
    void getAllCars() throws Exception {
        int offset = 0;
        int limit = 2;

        CarResponse response = defaultCarResponse();

        when(carService.getAllCars(offset, limit))
            .thenReturn(new PageResponse<>(offset, limit, 1, 1, "", List.of(response)));

        mockMvc.perform(get("/api/v1/cars")
                .param("offset", String.valueOf(offset))
                .param("limit", String.valueOf(limit))
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        verify(carService).getAllCars(offset, limit);
    }

    @Test
    void createCar() throws Exception {
        CarRequest request = defaultCarRequest();
        CarResponse response = defaultCarResponse();
        Long driverId = TEST_DRIVER_ID;

        when(carService.createCar(request, driverId)).thenReturn(response);

        mockMvc.perform(post("/api/v1/cars")
                .param("driverId", String.valueOf(driverId))
            .content(objectMapper.writeValueAsString(request))
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isCreated())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.carNumber").value(response.carNumber()))
            .andExpect(jsonPath("$.driverId").value(response.driverId()));

        verify(carService).createCar(request, driverId);
    }

    @Test
    void createCar_CarNumberExists_ThrowExceptions() throws Exception {
        CarRequest request = defaultCarRequest();
        Long driverId = TEST_DRIVER_ID;
        when(carService.createCar(request, driverId))
            .thenThrow(new CarNumberAlreadyException(
                ExceptionMessages.CONFLICT_MESSAGE.formatted("car")
            ));

        mockMvc.perform(post("/api/v1/cars")
            .param("driverId", String.valueOf(driverId))
                .content(objectMapper.writeValueAsString(request))
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isConflict())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void createCar_DriverNotFound_ThrowException() throws Exception {
        CarRequest request = defaultCarRequest();
        Long driverId = TEST_DRIVER_ID;

        when(carService.createCar(request, driverId)).thenThrow(new DriverNotFoundException(
            ExceptionMessages.NOT_FOUND_MESSAGE.formatted("driver", driverId)
        ));

        mockMvc.perform(post("/api/v1/cars")
            .param("driverId", String.valueOf(driverId))
                .content(objectMapper.writeValueAsString(request))
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void createCar_BadRequest_ThrowException() throws Exception {
        CarRequest invalidRequest = TestCarData.invalidRequest();
        Long driverId = TEST_DRIVER_ID;

        mockMvc.perform(post("/api/v1/cars")
            .param("driverId", String.valueOf(driverId))
                .content(objectMapper.writeValueAsString(invalidRequest))
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void updateCar() throws Exception {
        CarUpdateRequest request = updateRequest();
        CarResponse response = updatedResponse();
        Long id = TEST_CAR_ID;

        when(carService.updateCar(request, id)).thenReturn(response);

        mockMvc.perform(put("/api/v1/cars/{id}", id)
                .content(objectMapper.writeValueAsString(request))
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.carNumber").value(response.carNumber()))
            .andExpect(jsonPath("$.carBrand").value(response.carBrand()))
            .andExpect(jsonPath("$.carColor").value(response.carColor()));

        verify(carService).updateCar(request, id);
    }

    @Test
    void carUpdate_CarNotFound_ThrowException() throws Exception {
        CarUpdateRequest request = updateRequest();
        Long id = TEST_CAR_ID;

        when(carService.updateCar(request, id))
            .thenThrow(new CarNotFoundException(ExceptionMessages.NOT_FOUND_MESSAGE.formatted("car", id)));

        mockMvc.perform(put("/api/v1/cars/{id}", id)
            .content(objectMapper.writeValueAsString(request))
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void carUpdate_CarNumberExists_ThrowException() throws Exception {
        CarUpdateRequest request = updateRequest();
        Long id = TEST_CAR_ID;

        when(carService.updateCar(request, id))
            .thenThrow(new CarNumberAlreadyException(ExceptionMessages.CONFLICT_MESSAGE.formatted("car")));

        mockMvc.perform(put("/api/v1/cars/{id}", id)
            .content(objectMapper.writeValueAsString(request))
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isConflict())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void deleteCar() throws Exception {
        Long id = TEST_CAR_ID;

        mockMvc.perform(delete("/api/v1/cars/{id}", id)
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());
    }

    @Test
    void deleteCar_CarNotFound_ThrowException() throws Exception {
        Long id = TEST_CAR_ID;

        doThrow(new CarNotFoundException(ExceptionMessages.NOT_FOUND_MESSAGE.formatted("car", id)))
            .when(carService).deleteCar(id);

        mockMvc.perform(delete("/api/v1/cars/{id}", id)
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }
}