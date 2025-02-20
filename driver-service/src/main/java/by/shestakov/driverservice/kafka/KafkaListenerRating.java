package by.shestakov.driverservice.kafka;

import by.shestakov.driverservice.dto.request.DriverRatingRequest;
import by.shestakov.driverservice.entity.Driver;
import by.shestakov.driverservice.exception.driver.DriverNotFoundException;
import by.shestakov.driverservice.repository.DriverRepository;
import by.shestakov.driverservice.service.DriverService;
import by.shestakov.driverservice.util.ExceptionMessages;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KafkaListenerRating {

    private final DriverRepository driverRepository;

    @KafkaListener(topics = "update-rating-topic", groupId = "rating-consumer")
    public void updateRating(@Payload DriverRatingRequest request) {
        Driver existsDriver = driverRepository.findByIdAndIsDeletedFalse(request.id())
            .orElseThrow(() -> new DriverNotFoundException(
                ExceptionMessages.NOT_FOUND_MESSAGE.formatted("driver", request.id())));
        existsDriver.setRating(request.rating());
        driverRepository.save(existsDriver);
    }
}
