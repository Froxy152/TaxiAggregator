package by.shestakov.ridesservice.controller.impl;

import static testConstant.TestConstant.TEST_ID;
import static testConstant.TestConstant.defaultRideDriverNotFoundRequest;
import static testConstant.TestConstant.defaultRidePassengerNotFoundRequest;
import static testConstant.TestConstant.defaultRideRequest;
import static testConstant.TestConstant.defaultRideResponse;
import static testConstant.TestConstant.defaultRideStatusRequest;
import static testConstant.TestConstant.defaultRideUpdateRequest;
import static testConstant.TestConstant.invalidRequest;
import static testConstant.TestConstant.updatedRideResponse;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import by.shestakov.ridesservice.dto.request.RideRequest;
import by.shestakov.ridesservice.dto.request.RideStatusRequest;
import by.shestakov.ridesservice.dto.request.RideUpdateRequest;
import by.shestakov.ridesservice.dto.response.PageResponse;
import by.shestakov.ridesservice.dto.response.RideResponse;
import by.shestakov.ridesservice.exception.DataNotFoundException;
import by.shestakov.ridesservice.exception.DriverWithoutCarException;
import by.shestakov.ridesservice.exception.FeignNotFoundDataException;
import by.shestakov.ridesservice.exception.GlobalExceptionHandler;
import by.shestakov.ridesservice.service.RideService;
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
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ExtendWith(MockitoExtension.class)
class RideControllerImplTest {

    @InjectMocks
    private RideControllerImpl rideController;

    @Mock
    private RideService rideService;

    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(rideController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    @Test
    void getAll() throws Exception {
        int offset = 0;
        int limit = 2;

        RideResponse response = defaultRideResponse();

        when(rideService.getAll(offset, limit))
                .thenReturn(new PageResponse<>(offset, limit, 1, 1, "", List.of(response)));

        mockMvc.perform(get("/api/v1/rides")
                        .param("offset", String.valueOf(offset))
                        .param("limit", String.valueOf(limit)))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(rideService).getAll(offset, limit);
    }

    @Test
    void getById_ReturnValidResponse_200() throws Exception {
        String id = TEST_ID;
        RideResponse response = defaultRideResponse();

        when(rideService.getById(id)).thenReturn(response);

        mockMvc.perform(get("/api/v1/rides/{id}", id))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.pickUpAddress").value(response.pickUpAddress()))
                .andExpect(jsonPath("$.destinationAddress").value(response.destinationAddress()));

        verify(rideService).getById(id);
    }

    @Test
    void getById_RideNotFound_404() throws Exception {
        String id = TEST_ID;

        when(rideService.getById(id)).thenThrow(new DataNotFoundException());

        mockMvc.perform(get("/api/v1/rides/{id}", id))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(rideService).getById(id);
    }

    @Test
    void create_ReturnsValidResponse_201() throws Exception {
        RideRequest request = defaultRideRequest();
        RideResponse response = defaultRideResponse();

        when(rideService.createRide(request)).thenReturn(response);

        mockMvc.perform(post("/api/v1/rides")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.pickUpAddress").value(response.pickUpAddress()))
                .andExpect(jsonPath("$.destinationAddress").value(response.destinationAddress()));
    }

    @Test
    void create_DriverNotFound_404() throws Exception {
        RideRequest request = defaultRideDriverNotFoundRequest();

        when(rideService.createRide(request)).thenThrow(new FeignNotFoundDataException(""));

        mockMvc.perform(post("/api/v1/rides")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(rideService).createRide(request);
    }

    @Test
    void create_PassengerNotFound_404() throws Exception {
        RideRequest request = defaultRidePassengerNotFoundRequest();

        when(rideService.createRide(request)).thenThrow(new FeignNotFoundDataException(""));

        mockMvc.perform(post("/api/v1/rides")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(rideService).createRide(request);
    }

    @Test
    void create_DriverWithoutCar_400() throws Exception {
        RideRequest request = defaultRidePassengerNotFoundRequest();

        when(rideService.createRide(request)).thenThrow(new DriverWithoutCarException());

        mockMvc.perform(post("/api/v1/rides")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        verify(rideService).createRide(request);
    }

    @Test
    void create_BadRequest_400() throws Exception {
        RideRequest invalidRequest = invalidRequest();

        mockMvc.perform(post("/api/v1/rides")
                        .content(objectMapper.writeValueAsString(invalidRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void updateStatus_ReturnsValidResponse_200() throws Exception {
        RideStatusRequest request = defaultRideStatusRequest();
        RideResponse response = updatedRideResponse();
        String id = TEST_ID;

        when(rideService.changeStatus(request, id)).thenReturn(response);

        mockMvc.perform(patch("/api/v1/rides")
                        .param("rideId", id)
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.pickUpAddress").value(response.pickUpAddress()))
                .andExpect(jsonPath("$.destinationAddress").value(response.destinationAddress()));

        verify(rideService).changeStatus(request, id);
    }

    @Test
    void updateStatus_RideNotFound_404() throws Exception {
        RideStatusRequest request = defaultRideStatusRequest();
        String id = "invalid_id";

        when(rideService.changeStatus(request, id)).thenThrow(new DataNotFoundException());
        mockMvc.perform(patch("/api/v1/rides", id)
                        .param("rideId", id)
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(rideService).changeStatus(request, id);
    }

    @Test
    void updateRide_ReturnsValidResponse_200() throws Exception {
        String id = TEST_ID;
        RideUpdateRequest request = defaultRideUpdateRequest();
        RideResponse response = updatedRideResponse();

        when(rideService.updateRide(request, id)).thenReturn(response);

        mockMvc.perform(put("/api/v1/rides")
                        .param("rideId", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(rideService).updateRide(request, id);
    }

    @Test
    void updateRide_RideNotFound_404() throws Exception {
        String id = TEST_ID;
        RideUpdateRequest request = defaultRideUpdateRequest();

        when(rideService.updateRide(request, id)).thenThrow(new DataNotFoundException());

        mockMvc.perform(put("/api/v1/rides")
                        .param("rideId", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(rideService).updateRide(request, id);
    }
}