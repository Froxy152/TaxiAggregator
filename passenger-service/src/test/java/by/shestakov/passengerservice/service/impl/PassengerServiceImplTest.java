package by.shestakov.passengerservice.service.impl;

import static by.shestakov.passengerservice.constant.UnitTestConstants.TEST_ID;
import static by.shestakov.passengerservice.constant.UnitTestConstants.defaultPassenger;
import static by.shestakov.passengerservice.constant.UnitTestConstants.defaultRequest;
import static by.shestakov.passengerservice.constant.UnitTestConstants.defaultResponse;
import static by.shestakov.passengerservice.constant.UnitTestConstants.updatePassengerRequest;
import static by.shestakov.passengerservice.constant.UnitTestConstants.updatedPassengerResponse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import by.shestakov.passengerservice.dto.request.PassengerRequest;
import by.shestakov.passengerservice.dto.request.UpdatePassengerRequest;
import by.shestakov.passengerservice.dto.response.PageResponse;
import by.shestakov.passengerservice.dto.response.PassengerResponse;
import by.shestakov.passengerservice.entity.Passenger;
import by.shestakov.passengerservice.exception.PassengerAlreadyExistsException;
import by.shestakov.passengerservice.exception.PassengerNotFoundException;
import by.shestakov.passengerservice.mapper.PageMapper;
import by.shestakov.passengerservice.mapper.PassengerMapper;
import by.shestakov.passengerservice.repository.PassengerRepository;
import by.shestakov.passengerservice.util.ExceptionConstants;
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
class PassengerServiceImplTest {

    @InjectMocks
    private PassengerServiceImpl passengerService;

    @Mock
    private PassengerRepository passengerRepository;

    @Mock
    private PassengerMapper passengerMapper;

    @Mock
    private PageMapper pageMapper;

    @Test
    void testGetAllPassengers() {
        int offset = 0;
        int limit = 2;

        List<Passenger> passengers = List.of(defaultPassenger(), defaultPassenger());
        Page<Passenger> passengerPage = new PageImpl<>(passengers, PageRequest.of(offset, limit), passengers.size());

        List<PassengerResponse> passengerResponses = List.of(defaultResponse(), defaultResponse());
        Page<PassengerResponse> passengerResponsePage =
                new PageImpl<>(passengerResponses, PageRequest.of(offset, limit), passengerResponses.size());

        PageResponse<PassengerResponse> expectedPageResponse = PageResponse.<PassengerResponse>builder()
                .offset(offset)
                .limit(limit)
                .totalPages(passengerPage.getTotalPages())
                .totalElements(passengerPage.getTotalElements())
                .sort("")
                .values(passengerResponses)
                .build();
        Passenger passenger = defaultPassenger();
        PassengerResponse passengerResponse = defaultResponse();

        when(passengerRepository.findAllByIsDeletedFalse(PageRequest.of(offset, limit))).thenReturn(passengerPage);
        when(passengerMapper.toDto(any(Passenger.class))).thenReturn(passengerResponse);
        when(pageMapper.toDto(passengerResponsePage)).thenReturn(expectedPageResponse);

        PageResponse<PassengerResponse> actualResponse = passengerService.getAllPassengers(offset, limit);

        assertNotNull(actualResponse);
        assertEquals(expectedPageResponse, actualResponse);

        verify(passengerRepository).findAllByIsDeletedFalse(PageRequest.of(offset, limit));
        verify(passengerMapper, times(passengers.size())).toDto(any(Passenger.class));
        verify(pageMapper).toDto(any(Page.class));
    }

    @Test
    void testGetPassengerById_ReturnsValidPassengerResponse() {

        Long id = TEST_ID;
        Passenger passenger = defaultPassenger();
        PassengerResponse passengerResponse = defaultResponse();

        when(passengerRepository.findByIdAndIsDeletedFalse(id)).thenReturn(Optional.of(passenger));
        when(passengerMapper.toDto(passenger)).thenReturn(passengerResponse);

        PassengerResponse response = passengerService.getPassengerById(id);

        assertNotNull(response);
        assertEquals(passengerResponse.email(), response.email());
        assertEquals(passengerResponse.phoneNumber(), response.phoneNumber());

        verify(passengerRepository).findByIdAndIsDeletedFalse(id);
        verify(passengerMapper).toDto(passenger);
    }

    @Test
    void testGetPassengerById_PassengerNotFound_throwsPassengerNotFoundException() {
        Long passengerId = TEST_ID;

        when(passengerRepository.findByIdAndIsDeletedFalse(passengerId)).thenReturn(Optional.empty());

        assertThrows(
                PassengerNotFoundException.class,
                () -> passengerService.getPassengerById(passengerId));

        verify(passengerRepository).findByIdAndIsDeletedFalse(passengerId);
    }

    @Test
    void testCreatePassenger_ReturnValidResponse() {
        PassengerRequest passengerRequest = defaultRequest();
        Passenger passenger = defaultPassenger();
        PassengerResponse passengerResponse = defaultResponse();

        when(passengerMapper.toEntity(passengerRequest)).thenReturn(passenger);
        when(passengerRepository.save(passenger)).thenReturn(passenger);
        when(passengerMapper.toDto(passenger)).thenReturn(passengerResponse);

        PassengerResponse response = passengerService.createPassenger(passengerRequest);

        assertNotNull(response);
        assertEquals(passengerResponse.name(), response.name());
        assertEquals(passengerResponse.lastName(), response.lastName());
        assertEquals(passengerResponse.phoneNumber(), response.phoneNumber());
        assertEquals(passengerResponse.email(), response.email());
        assertEquals(passengerResponse.isDeleted(), response.isDeleted());
        assertEquals(passengerResponse.rating(), response.rating());

        verify(passengerRepository).save(passenger);
        verify(passengerMapper).toDto(passenger);
    }

    @Test
    void testCreatePassenger_PassengerAlreadyExists_throwPassengerAlreadyExistsException() {
        PassengerRequest passengerRequest = defaultRequest();

        when(passengerRepository.existsByEmailOrPhoneNumber(passengerRequest.email(),
            passengerRequest.phoneNumber())).thenReturn(true);

        PassengerAlreadyExistsException exception = assertThrows(
                PassengerAlreadyExistsException.class,
                () -> passengerService.createPassenger(passengerRequest));

        assertEquals(
                ExceptionConstants.CONFLICT_MESSAGE.formatted(passengerRequest.email(), passengerRequest.phoneNumber()),
                exception.getMessage()
        );

    }

    @Test
    void testUpdatePassengerById() {
        Long passengerId = TEST_ID;
        Passenger passenger = defaultPassenger();
        UpdatePassengerRequest updatePassengerRequest = updatePassengerRequest();
        PassengerResponse updatePassengerResponse = updatedPassengerResponse();

        when(passengerRepository.findByIdAndIsDeletedFalse(passengerId)).thenReturn(Optional.of(passenger));
        when(passengerMapper.toDto(passenger)).thenReturn(updatePassengerResponse);

        PassengerResponse response = passengerService.updatePassengerById(updatePassengerRequest, passengerId);

        assertNotNull(response);
        assertEquals(updatePassengerResponse.name(), response.name());
        assertEquals(updatePassengerResponse.lastName(), response.lastName());
        assertEquals(updatePassengerResponse.email(), response.email());
        assertEquals(updatePassengerResponse.phoneNumber(), response.phoneNumber());

        verify(passengerMapper).update(updatePassengerRequest, passenger);
        verify(passengerRepository).save(passenger);
    }

    @Test
    void testUpdatePassengerById_PassengerNotFound_ThrowPassengerNotFoundException() {
        Long passengerId = TEST_ID;
        UpdatePassengerRequest updatePassengerRequest = updatePassengerRequest();

        when(passengerRepository.findByIdAndIsDeletedFalse(passengerId)).thenReturn(Optional.empty());

        PassengerNotFoundException exception = assertThrows(
                PassengerNotFoundException.class,
                () -> passengerService.updatePassengerById(updatePassengerRequest, passengerId)
        );

        assertEquals(ExceptionConstants.NOT_FOUND_MESSAGE.formatted(passengerId), exception.getMessage());
    }

    @Test
    void testUpdatePassengerById_PassengerExistByEmail_ThrowPassengerAlreadyExists() {
        Long passengerId = TEST_ID;
        Passenger passenger = defaultPassenger();
        UpdatePassengerRequest updatePassengerRequest = updatePassengerRequest();

        when(passengerRepository.findByIdAndIsDeletedFalse(passengerId)).thenReturn(Optional.of(passenger));
        when(passengerRepository.existsByEmail(updatePassengerRequest.email())).thenReturn(true);

        PassengerAlreadyExistsException exception = assertThrows(PassengerAlreadyExistsException.class, () ->
            passengerService.updatePassengerById(updatePassengerRequest, passengerId)
        );

        assertEquals(ExceptionConstants.CONFLICT_DATA_ALREADY_REGISTERED
            .formatted(updatePassengerRequest.email()), exception.getMessage());
    }

    @Test
    void testUpdatePassengerById_PassengerExistsByPhoneNumber_ThrowPassengerAlreadyExists() {
        Long passengerId = TEST_ID;
        Passenger passenger = defaultPassenger();
        UpdatePassengerRequest updatePassengerRequest = updatePassengerRequest();

        when(passengerRepository.findByIdAndIsDeletedFalse(passengerId)).thenReturn(Optional.of(passenger));
        when(passengerRepository.existsByEmail(updatePassengerRequest.email())).thenReturn(false);
        when(passengerRepository.existsByPhoneNumber(updatePassengerRequest.phoneNumber())).thenReturn(true);

        PassengerAlreadyExistsException exception = assertThrows(
                PassengerAlreadyExistsException.class,
                () -> passengerService.updatePassengerById(updatePassengerRequest, passengerId)
        );

        assertEquals(ExceptionConstants.CONFLICT_DATA_ALREADY_REGISTERED
            .formatted(updatePassengerRequest.phoneNumber()), exception.getMessage());
    }

    @Test
    void testSoftDeletePassenger() {
        Long passengerId = TEST_ID;
        Passenger passenger = defaultPassenger();

        when(passengerRepository.findByIdAndIsDeletedFalse(passengerId)).thenReturn(Optional.of(passenger));

        passengerService.softDeletePassenger(passengerId);

        assertTrue(passenger.getIsDeleted());
        verify(passengerRepository).save(passenger);
    }

    @Test
    void testSoftDeletePassenger_PassengerNotFound_ThrowPassengerNotFound() {
        Long passengerId = TEST_ID;

        when(passengerRepository.findByIdAndIsDeletedFalse(passengerId)).thenReturn(Optional.empty());

        PassengerNotFoundException exception = assertThrows(
                PassengerNotFoundException.class,
                () -> passengerService.softDeletePassenger(passengerId)
        );

        assertEquals(ExceptionConstants.NOT_FOUND_MESSAGE.formatted(passengerId), exception.getMessage());
    }
}