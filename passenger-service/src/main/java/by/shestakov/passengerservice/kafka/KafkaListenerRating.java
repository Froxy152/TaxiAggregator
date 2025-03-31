package by.shestakov.passengerservice.kafka;

import by.shestakov.passengerservice.dto.request.UpdateRatingRequest;
import by.shestakov.passengerservice.entity.Passenger;
import by.shestakov.passengerservice.exception.PassengerNotFoundException;
import by.shestakov.passengerservice.repository.PassengerRepository;
import by.shestakov.passengerservice.util.ExceptionConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@EnableKafka
@RequiredArgsConstructor
public class KafkaListenerRating {

    private final PassengerRepository passengerRepository;

    @KafkaListener(topics = "update-passenger-rating-topic", groupId = "rating-consumer")
    public void updateRating(@Payload UpdateRatingRequest updateRatingRequest) {
        log.info("Message from Kafka received UpdateRatingRequest: {}", updateRatingRequest);
        Passenger existsPassenger = passengerRepository.findByIdAndIsDeletedFalse(updateRatingRequest.id())
            .orElseThrow(() -> new PassengerNotFoundException(
                ExceptionConstants.NOT_FOUND_MESSAGE.formatted(updateRatingRequest.id())
            ));
        existsPassenger.setRating(updateRatingRequest.rating());
        log.info("updateRating: Passenger rating is updated. Passenger: {}", existsPassenger);
        passengerRepository.save(existsPassenger);
    }

}
