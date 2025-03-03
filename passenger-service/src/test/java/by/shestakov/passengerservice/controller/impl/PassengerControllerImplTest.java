package by.shestakov.passengerservice.controller.impl;

import static by.shestakov.passengerservice.constant.UnitTestConstants.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import by.shestakov.passengerservice.dto.request.PassengerRequest;
import by.shestakov.passengerservice.dto.request.UpdatePassengerRequest;
import by.shestakov.passengerservice.dto.response.PageResponse;
import by.shestakov.passengerservice.dto.response.PassengerResponse;
import by.shestakov.passengerservice.entity.Passenger;
import by.shestakov.passengerservice.exception.GlobalExceptionHandler;
import by.shestakov.passengerservice.exception.PassengerNotFoundException;
import by.shestakov.passengerservice.service.PassengerService;
import by.shestakov.passengerservice.service.impl.PassengerServiceImpl;
import by.shestakov.passengerservice.util.ExceptionConstants;
import by.shestakov.passengerservice.util.ValidationConstants;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.text.similarity.JaccardSimilarity;
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


import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class PassengerControllerImplTest{

    @Mock
    private PassengerService passengerService;

    @InjectMocks
    private PassengerControllerImpl passengerController;

    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();



    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(passengerController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    @Test
    void testGetAll_ReturnsValidResponse() throws Exception {
        int offset = 0;
        int limit = 2;
        PassengerResponse response = defaultResponse();

        when(passengerService.getAllPassengers(offset, limit)).
                thenReturn(new PageResponse<>(
                        offset ,1, 1, 1,  "", List.of(response)));

        mockMvc.perform(get("/api/v1/passengers")
                .param("offset", String.valueOf(offset))
                .param("limit", String.valueOf(limit)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        verify(passengerService).getAllPassengers(offset, limit);
    }


    @Test
    void testGetById_ReturnsValidResponse() throws Exception {
        Long id = TEST_ID;
        PassengerResponse response = defaultResponse();

        when(passengerService.getPassengerById(id)).thenReturn(response);

        mockMvc.perform(get("/api/v1/passengers/{id}", id))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        verify(passengerService).getPassengerById(id);
    }

    @Test
    void testGetByIdTest_InvalidId() throws Exception {
        String invalidId = "invalidId";
        mockMvc.perform(get("/api/v1/passengers/{id}", invalidId)) //todo ready to delete
                .andExpect(status().isBadRequest());
    }

    @Test
    void testGetById_PassengerNotFound_throwException() throws Exception {
        Long id = 2L;
        when(passengerService.getPassengerById(id))
                .thenThrow(new PassengerNotFoundException(
                        ExceptionConstants.NOT_FOUND_MESSAGE.formatted(id)));

        mockMvc.perform(get("/api/v1/passengers/{id}", id))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        verify(passengerService).getPassengerById(id);
    }


    @Test
    void testCreate_ReturnsValidRequestWithResponse() throws Exception {
        PassengerRequest request = defaultRequest();
        PassengerResponse response = defaultResponse();

        when(passengerService.createPassenger(request)).thenReturn(response);

        mockMvc.perform(post("/api/v1/passengers")
                        .content(objectMapper.writeValueAsString(defaultRequest()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void testCreate_InvalidEmailInRequest() throws Exception {
        PassengerRequest invalidRequest = invalidEmailRequest();

        mockMvc.perform(post("/api/v1/passengers")
                .content(objectMapper.writeValueAsString(invalidRequest))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void testCreate_InvalidPhoneNumberRequest() throws Exception {
        PassengerRequest invalidRequest = invalidPhoneNumberRequest();

        mockMvc.perform(post("/api/v1/passengers")
                .content(objectMapper.writeValueAsString(invalidRequest))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void testUpdate_ReturnsValidResponse() throws Exception{
        UpdatePassengerRequest request = updatePassengerRequest();
        PassengerResponse updatedResponse = updatedPassengerResponse();

        Long id = TEST_ID;

        when(passengerService.updatePassengerById(request, id)).thenReturn(updatedResponse);

        mockMvc.perform(put("/api/v1/passengers/{id}", id)
                .content(objectMapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        verify(passengerService).updatePassengerById(request,id);
    }

    @Test
    void testUpdateById_NotFoundPassenger_ThrowException() throws Exception {
        UpdatePassengerRequest request = updatePassengerRequest();

        Long id = 2L;

        when(passengerService.updatePassengerById(request, id)).thenThrow(
                 new PassengerNotFoundException(
                         ExceptionConstants.NOT_FOUND_MESSAGE.formatted(id))
        );

        mockMvc.perform(put("/api/v1/passengers/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(passengerService).updatePassengerById(request, id);
    }

    @Test
    void testDelete_ReturnValidResponse() throws Exception {
        Long id = TEST_ID;

        mockMvc.perform(delete("/api/v1/passengers/{id}", id)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    void testDelete_NotFoundPassenger_ThrowException() throws Exception {
        Long id = 2L;

        doThrow(new PassengerNotFoundException(ExceptionConstants.NOT_FOUND_MESSAGE.formatted(id)))
                .when(passengerService).softDeletePassenger(id);

        mockMvc.perform(delete("/api/v1/passengers/{id}", id))
                .andExpect(status().isNotFound());

        verify(passengerService).softDeletePassenger(id);
    }
}