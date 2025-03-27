package by.shestakov.ratingservice.kafka;

import by.shestakov.ratingservice.dto.request.UpdateRatingRequest;
import java.math.BigDecimal;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KafkaProducer {

    private final KafkaTemplate<String, UpdateRatingRequest> kafkaTemplate;

    @Value("${spring.kafka.topics.driver-rating}")
    private String driverTopic;

    @Value("${spring.kafka.topics.passenger-rating}")
    private String passengerTopic;

    public void sendMessageForDriver(Long driverId, BigDecimal rating) {

        UpdateRatingRequest message =  UpdateRatingRequest.builder()
            .id(driverId)
            .rating(rating)
            .build();
        System.out.println("Message was send: " + message.rating());
        kafkaTemplate.send(driverTopic, message);
    }

    public void sendMessageForPassenger(Long passengerId, BigDecimal rating) {

        UpdateRatingRequest message = UpdateRatingRequest.builder()
            .id(passengerId)
            .rating(rating)
            .build();
        System.out.println("Message was send: " + message.rating());
        kafkaTemplate.send(passengerTopic, message);
    }
}
