package by.shestakov.driverservice.kafka;

import static by.shestakov.constant.TestDriverData.defaultDriver;
import static by.shestakov.constant.TestKafkaData.ratingRequest;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import by.shestakov.driverservice.dto.request.UpdateRatingRequest;
import by.shestakov.driverservice.entity.Driver;
import by.shestakov.driverservice.exception.driver.DriverNotFoundException;
import by.shestakov.driverservice.repository.DriverRepository;
import by.shestakov.driverservice.util.ExceptionMessages;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class KafkaListenerRatingTest {

    @InjectMocks
    private KafkaListenerRating kafkaListenerRating;

    @Mock
    private DriverRepository driverRepository;

    @Test
    void updateRating() {
        UpdateRatingRequest request = ratingRequest();
        Driver driver = defaultDriver();

        when(driverRepository.findByIdAndIsDeletedFalse(ratingRequest().id())).thenReturn(Optional.of(driver));

        kafkaListenerRating.updateRating(request);

        assertEquals(ratingRequest().rating(), driver.getRating());

        verify(driverRepository).save(driver);
    }

    @Test
    void updateRating_DriverNotFound() {
        UpdateRatingRequest request = ratingRequest();

        when(driverRepository.findByIdAndIsDeletedFalse(request.id())).thenReturn(Optional.empty());

        DriverNotFoundException exception = assertThrows(
            DriverNotFoundException.class,
            () -> kafkaListenerRating.updateRating(request)
        );

        assertEquals(ExceptionMessages.NOT_FOUND_MESSAGE.formatted("driver", request.id()), exception.getMessage());
    }
}