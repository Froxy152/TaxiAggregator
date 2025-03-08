package by.shestakov.passengerservice.controller.impl;


import static by.shestakov.passengerservice.constant.UnitTestConstants.TEST_ALREADY_ID;
import static by.shestakov.passengerservice.constant.UnitTestConstants.TEST_ID;
import static by.shestakov.passengerservice.constant.UnitTestConstants.TEST_INVALID_ID;
import static by.shestakov.passengerservice.constant.UnitTestConstants.defaultRequest;
import static by.shestakov.passengerservice.constant.UnitTestConstants.defaultResponse;
import static by.shestakov.passengerservice.constant.UnitTestConstants.invalidEmailRequest;
import static by.shestakov.passengerservice.constant.UnitTestConstants.invalidPhoneNumberRequest;
import static by.shestakov.passengerservice.constant.UnitTestConstants.updateAlreadyEmailPassengerRequest;
import static by.shestakov.passengerservice.constant.UnitTestConstants.updateAlreadyNumberPassengerRequest;
import static by.shestakov.passengerservice.constant.UnitTestConstants.updatePassengerRequest;
import static by.shestakov.passengerservice.constant.UnitTestConstants.updatedPassengerResponse;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import by.shestakov.passengerservice.dto.request.PassengerRequest;
import by.shestakov.passengerservice.dto.request.UpdatePassengerRequest;
import by.shestakov.passengerservice.dto.response.PageResponse;
import by.shestakov.passengerservice.dto.response.PassengerResponse;
import by.shestakov.passengerservice.exception.GlobalExceptionHandler;
import by.shestakov.passengerservice.exception.PassengerAlreadyExistsException;
import by.shestakov.passengerservice.exception.PassengerNotFoundException;
import by.shestakov.passengerservice.service.PassengerService;
import by.shestakov.passengerservice.util.ExceptionConstants;
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
class PassengerControllerImplTest {

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

        when(passengerService.getAllPassengers(offset, limit))
            .thenReturn(new PageResponse<>(
                offset, 1, 1, 1, "", List.of(response)));

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
    void testGetById_PassengerNotFound_throwException() throws Exception {
        Long id = TEST_INVALID_ID;
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
    void testCreate_PassengerCredentialsAlreadyExists_ThrowException() throws Exception {
        PassengerRequest request = defaultRequest();

        when(passengerService.createPassenger(request)).thenThrow(new PassengerAlreadyExistsException(
            ExceptionConstants.CONFLICT_MESSAGE.formatted(request.phoneNumber(), request.email())));

        mockMvc.perform(post("/api/v1/passengers")
                .content(objectMapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isConflict())
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
    void testUpdate_ReturnsValidResponse() throws Exception {
        UpdatePassengerRequest request = updatePassengerRequest();
        PassengerResponse updatedResponse = updatedPassengerResponse();

        Long id = TEST_ID;

        when(passengerService.updatePassengerById(request, id)).thenReturn(updatedResponse);

        mockMvc.perform(put("/api/v1/passengers/{id}", id)
                .content(objectMapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        verify(passengerService).updatePassengerById(request, id);
    }

    @Test
    void testUpdateById_NotFoundPassenger_ThrowException() throws Exception {
        UpdatePassengerRequest request = updatePassengerRequest();

        Long id = TEST_INVALID_ID;

        when(passengerService.updatePassengerById(request, id)).thenThrow(
            new PassengerNotFoundException(ExceptionConstants.NOT_FOUND_MESSAGE.formatted(id))
        );

        mockMvc.perform(put("/api/v1/passengers/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound());

        verify(passengerService).updatePassengerById(request, id);
    }

    @Test
    void testUpdateById_PassengerNumberAlreadyExists_ThrowException() throws Exception {
        UpdatePassengerRequest request = updateAlreadyNumberPassengerRequest();

        Long id = TEST_ALREADY_ID;

        when(passengerService.updatePassengerById(request, id)).thenThrow(
            new PassengerAlreadyExistsException(
                ExceptionConstants.CONFLICT_DATA_ALREADY_REGISTERED.formatted(request.phoneNumber()))
        );

        mockMvc.perform(put("/api/v1/passengers/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isConflict());
    }

    @Test
    void testUpdateById_PassengerEmailAlreadyExists_ThrowException() throws Exception {
        UpdatePassengerRequest request = updateAlreadyEmailPassengerRequest();

        Long id = TEST_ALREADY_ID;

        when(passengerService.updatePassengerById(request, id)).thenThrow(
            new PassengerAlreadyExistsException(
                ExceptionConstants.CONFLICT_DATA_ALREADY_REGISTERED.formatted(request.email()))
        );

        mockMvc.perform(put("/api/v1/passengers/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isConflict());
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