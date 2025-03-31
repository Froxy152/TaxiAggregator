package by.shestakov.driverservice.kafka;

import by.shestakov.driverservice.dto.request.UpdateRatingRequest;
import by.shestakov.driverservice.entity.Driver;
import by.shestakov.driverservice.exception.driver.DriverNotFoundException;
import by.shestakov.driverservice.repository.DriverRepository;
import by.shestakov.driverservice.util.ExceptionMessages;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaListenerRating {

    private final DriverRepository driverRepository;

    @KafkaListener(topics = "update-rating-topic", groupId = "rating-consumer-1")
    public void updateRating(@Payload UpdateRatingRequest request) {
        log.info("Message from Kafka received UpdateRatingRequest: {}", request);
        Driver existsDriver = driverRepository.findByIdAndIsDeletedFalse(request.id())
            .orElseThrow(() -> new DriverNotFoundException(
                ExceptionMessages.NOT_FOUND_MESSAGE.formatted("driver", request.id())));
        existsDriver.setRating(request.rating());

        log.info("updateRating: Driver rating is updated. Driver: {}", existsDriver);
        driverRepository.save(existsDriver);
    }
}
