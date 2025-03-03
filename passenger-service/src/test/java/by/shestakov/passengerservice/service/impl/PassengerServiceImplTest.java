package by.shestakov.passengerservice.service.impl;

import by.shestakov.passengerservice.dto.request.PassengerRequest;
import by.shestakov.passengerservice.dto.request.UpdatePassengerRequest;
import by.shestakov.passengerservice.dto.response.PassengerResponse;
import by.shestakov.passengerservice.entity.Passenger;
import by.shestakov.passengerservice.exception.PassengerAlreadyExistsException;
import by.shestakov.passengerservice.exception.PassengerNotFoundException;
import by.shestakov.passengerservice.mapper.PassengerMapper;
import by.shestakov.passengerservice.repository.PassengerRepository;
import by.shestakov.passengerservice.service.PassengerService;
import by.shestakov.passengerservice.util.ExceptionConstants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PassengerServiceImplTest {

    @InjectMocks
    private PassengerServiceImpl passengerService;

    @Mock
    private PassengerRepository passengerRepository;

    @Mock
    private PassengerMapper passengerMapper;

    private PassengerRequest passengerRequest;
    private UpdatePassengerRequest updatePassengerRequest;
    private Passenger passenger;
    private PassengerResponse passengerResponse;
    private PassengerResponse updatePassengerResponse;
    private Long passengerId;



    @BeforeEach
    void setUp() {
        passengerRequest = new PassengerRequest("Ilya", "Shestakov", "test@gmail.com", "+375298956845");

        updatePassengerRequest = new UpdatePassengerRequest("Nikita", "shestakov", "test@example.com", "+375256985235");

        passengerId = 23L;

        passenger = new Passenger();
        passenger.setId(15L);
        passenger.setName("Jeremy");
        passenger.setLastName("Barak");
        passenger.setEmail("test.mail@gmail.com");
        passenger.setPhoneNumber("+375295035859");

        passengerResponse = new PassengerResponse(32L, "Ilya","Shestakov", "mail@example.com","+375445684123",new BigDecimal("0.0"), false);

        updatePassengerResponse = new PassengerResponse(32L, "Nikita","shestakov", "test@example.com","+375256985235",new BigDecimal("0.0"), false);


    }

    @Test
    void testGetAllPassengers() {
    }

    @Test
    void testGetPassengerById() {
            when(passengerRepository.findByIdAndIsDeletedFalse(passengerId)).thenReturn(Optional.of(passenger));
            when(passengerMapper.toDto(passenger)).thenReturn(passengerResponse);

            PassengerResponse response = passengerService.getPassengerById(passengerId);

            assertNotNull(response);
            assertEquals(passengerResponse.email(), response.email());
            assertEquals(passengerResponse.phoneNumber(), response.phoneNumber());

            verify(passengerRepository).findByIdAndIsDeletedFalse(passengerId);
            verify(passengerMapper).toDto(passenger);
    }

    @Test
    void testGetPassengerById_throwsPassengerNotFoundException() {
        when(passengerRepository.findByIdAndIsDeletedFalse(passengerId)).thenReturn(Optional.empty());

        assertThrows(PassengerNotFoundException.class, () -> passengerService.getPassengerById(passengerId));

        verify(passengerRepository).findByIdAndIsDeletedFalse(passengerId);
    }

    @Test
    void testCreatePassenger() {
        when(passengerMapper.toEntity(passengerRequest)).thenReturn(passenger);
        when(passengerRepository.save(passenger)).thenReturn(passenger);
        when(passengerMapper.toDto(passenger)).thenReturn(passengerResponse);

        PassengerResponse response = passengerService.createPassenger(passengerRequest);

        assertNotNull(response);
        assertEquals(passengerResponse.phoneNumber(), response.phoneNumber());
        assertEquals(passengerResponse.email(), response.email());

        verify(passengerRepository).save(passenger);
        verify(passengerMapper).toDto(passenger);
    }

    @Test
    void testCreatePassenger_throwPassengerAlReadyExistsException() {
        when(passengerRepository.existsByEmailOrPhoneNumber(passengerRequest.email(),passengerRequest.phoneNumber())).thenReturn(true);

        PassengerAlreadyExistsException exception = assertThrows(
                PassengerAlreadyExistsException.class,
                () -> passengerService.createPassenger(passengerRequest));

        assertEquals(ExceptionConstants.CONFLICT_MESSAGE.formatted(passengerRequest.email(), passengerRequest.phoneNumber()),
                exception.getMessage());
    }

    @Test
    void testUpdatePassengerById() {
        when(passengerRepository.findByIdAndIsDeletedFalse(passengerId)).thenReturn(Optional.of(passenger));
        when(passengerRepository.save(passenger)).thenReturn(passenger);
        when(passengerMapper.toDto(passenger)).thenReturn(updatePassengerResponse);

        PassengerResponse response = passengerService.updatePassengerById(updatePassengerRequest,passengerId);

        assertNotNull(response);
        assertEquals(updatePassengerRequest.email(), response.email());
        assertEquals(updatePassengerRequest.phoneNumber(), response.phoneNumber());

        verify(passengerMapper).update(updatePassengerRequest,passenger);
        verify(passengerRepository).save(passenger);
    }

    @Test
    void testSoftDeletePassenger() {
    }
}