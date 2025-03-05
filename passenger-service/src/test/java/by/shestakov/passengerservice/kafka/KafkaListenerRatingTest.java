package by.shestakov.passengerservice.kafka;

import static by.shestakov.passengerservice.constant.UnitTestConstants.defaultPassenger;
import static by.shestakov.passengerservice.constant.UnitTestConstants.ratingRequest;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import by.shestakov.passengerservice.dto.request.UpdateRatingRequest;
import by.shestakov.passengerservice.entity.Passenger;
import by.shestakov.passengerservice.exception.PassengerNotFoundException;
import by.shestakov.passengerservice.repository.PassengerRepository;
import by.shestakov.passengerservice.util.ExceptionConstants;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class KafkaListenerRatingTest {

    @Mock
    private PassengerRepository passengerRepository;

    @InjectMocks
    private KafkaListenerRating kafkaListenerRating;

    @Test
    void updateRating() {
        UpdateRatingRequest request = ratingRequest();
        Passenger passenger = defaultPassenger();

        when(passengerRepository.findByIdAndIsDeletedFalse(request.id())).thenReturn(Optional.of(passenger));

        kafkaListenerRating.updateRating(request);

        assertEquals(request.rating(), passenger.getRating());

        verify(passengerRepository).save(passenger);
    }

    @Test
    void updateRating_PassengerNotFound_ThrowException() {
        UpdateRatingRequest request = ratingRequest();

        when(passengerRepository.findByIdAndIsDeletedFalse(request.id())).thenReturn(Optional.empty());

        PassengerNotFoundException exception = assertThrows(
            PassengerNotFoundException.class,
            () -> kafkaListenerRating.updateRating(request)
        );

        assertEquals(ExceptionConstants.NOT_FOUND_MESSAGE.formatted(request.id()), exception.getMessage());


    }
}