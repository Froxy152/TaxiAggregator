package by.shestakov.passengerservice.kafka;

import by.shestakov.passengerservice.dto.request.UpdateRatingRequest;
import by.shestakov.passengerservice.entity.Passenger;
import by.shestakov.passengerservice.exception.PassengerNotFoundException;
import by.shestakov.passengerservice.repository.PassengerRepository;
import by.shestakov.passengerservice.util.ExceptionConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@EnableKafka
@RequiredArgsConstructor
public class KafkaListenerRating {

    private final PassengerRepository passengerRepository;

    @KafkaListener(topics = "update-passenger-rating-topic", groupId = "rating-consumer")
    public void updateRating(@Payload UpdateRatingRequest updateRatingRequest) {
        Passenger existsPassenger = passengerRepository.findByIdAndIsDeletedFalse(updateRatingRequest.id())
            .orElseThrow(() -> new PassengerNotFoundException(
                ExceptionConstants.NOT_FOUND_MESSAGE.formatted(updateRatingRequest.id())
            ));
        existsPassenger.setRating(updateRatingRequest.rating());

        passengerRepository.save(existsPassenger);
    }

}
