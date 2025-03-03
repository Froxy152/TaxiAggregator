package by.shestakov.passengerservice.service.impl;

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
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
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

    private PassengerRequest passengerRequest;
    private UpdatePassengerRequest updatePassengerRequest;
    private Passenger passenger;
    private PassengerResponse passengerResponse;
    private PassengerResponse updatePassengerResponse;
    private Long passengerId;
    private Integer limit;
    private Integer offset;
    private PageRequest pageRequest;
    private PageResponse<PassengerResponse> expectedPageResponse;
    private List<Passenger> passengers;
    private Page<Passenger> passengerPage;
    private List<PassengerResponse> passengerResponses;
    private Page<PassengerResponse> passengerResponsePage;


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

        passengerResponse = new PassengerResponse(32L, "Ilya", "Shestakov",
            "mail@example.com", "+375445684123", new BigDecimal("0.0"), false);

        updatePassengerResponse = new PassengerResponse(32L, "Nikita", "shestakov",
            "test@example.com", "+375256985235", new BigDecimal("0.0"), false);

        offset = 0;
        limit = 2;

        pageRequest = PageRequest.of(offset, limit);
        passengers = List.of(passenger, passenger);
        passengerPage = new PageImpl<>(passengers, pageRequest, passengers.size());

        passengerResponses = List.of(passengerResponse, passengerResponse);
        passengerResponsePage = new PageImpl<>(passengerResponses, pageRequest, passengerResponses.size());

        expectedPageResponse = PageResponse.<PassengerResponse>builder()
                .offset(offset)
                .limit(limit)
                .totalPages(passengerPage.getTotalPages())
                .totalElements(passengerPage.getTotalElements())
                .sort(pageRequest.getSort().toString())
                .values(passengerResponses)
                .build();

    }

    @Test
    void testGetAllPassengers() {
        when(passengerRepository.findAllByIsDeletedFalse(pageRequest)).thenReturn(passengerPage);
        when(passengerMapper.toDto(passenger)).thenReturn(passengerResponse);
        when(pageMapper.toDto(passengerResponsePage)).thenReturn(expectedPageResponse);

        PageResponse<PassengerResponse> actualResponse = passengerService.getAllPassengers(offset, limit);

        assertNotNull(actualResponse);
        assertEquals(expectedPageResponse, actualResponse);

        verify(passengerRepository).findAllByIsDeletedFalse(pageRequest);
        verify(passengerMapper, times(passengers.size())).toDto(any(Passenger.class));
        verify(pageMapper).toDto(any(Page.class));
    }

    @Test
    void testGetPassengerById_ReturnsValidPassengerResponse() {
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
    void testGetPassengerById_PassengerNotFound_throwsPassengerNotFoundException() {
        when(passengerRepository.findByIdAndIsDeletedFalse(passengerId)).thenReturn(Optional.empty());

        assertThrows(PassengerNotFoundException.class, () -> passengerService.getPassengerById(passengerId));

        verify(passengerRepository).findByIdAndIsDeletedFalse(passengerId);
    }

    @Test
    void testCreatePassenger_ReturnValidResponse() {
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
        when(passengerRepository.findByIdAndIsDeletedFalse(passengerId)).thenReturn(Optional.of(passenger));
        when(passengerRepository.save(passenger)).thenReturn(passenger);
        when(passengerMapper.toDto(passenger)).thenReturn(updatePassengerResponse);

        PassengerResponse response = passengerService.updatePassengerById(updatePassengerRequest, passengerId);

        assertNotNull(response);
        assertEquals(updatePassengerRequest.name(), response.name());
        assertEquals(updatePassengerRequest.lastName(), response.lastName());
        assertEquals(updatePassengerRequest.email(), response.email());
        assertEquals(updatePassengerRequest.phoneNumber(), response.phoneNumber());

        verify(passengerMapper).update(updatePassengerRequest, passenger);
        verify(passengerRepository).save(passenger);
    }

    @Test
    void testUpdatePassengerById_PassengerNotFound_ThrowPassengerNotFoundException() {
        when(passengerRepository.findByIdAndIsDeletedFalse(passengerId)).thenReturn(Optional.empty());

        PassengerNotFoundException exception = assertThrows(
                PassengerNotFoundException.class,
                () -> passengerService.updatePassengerById(updatePassengerRequest, passengerId)
        );

        assertEquals(ExceptionConstants.NOT_FOUND_MESSAGE.formatted(passengerId), exception.getMessage());
    }

    @Test
    void testUpdatePassengerById_PassengerExistByEmail_ThrowPassengerAlreadyExists() {
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
        when(passengerRepository.findByIdAndIsDeletedFalse(passengerId)).thenReturn(Optional.of(passenger));

        passengerService.softDeletePassenger(passengerId);

        assertTrue(passenger.getIsDeleted());
        verify(passengerRepository).save(passenger);
    }

    @Test
    void testSoftDeletePassenger_PassengerNotFound_ThrowPassengerNotFound() {
        when(passengerRepository.findByIdAndIsDeletedFalse(passengerId)).thenReturn(Optional.empty());

        PassengerNotFoundException exception = assertThrows(
                PassengerNotFoundException.class,
                () -> passengerService.softDeletePassenger(passengerId)
        );

        assertEquals(ExceptionConstants.NOT_FOUND_MESSAGE.formatted(passengerId), exception.getMessage());
    }
}