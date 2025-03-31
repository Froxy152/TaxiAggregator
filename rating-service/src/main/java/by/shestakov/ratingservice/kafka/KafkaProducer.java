package by.shestakov.ratingservice.kafka;

import by.shestakov.ratingservice.dto.request.UpdateRatingRequest;
import java.math.BigDecimal;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
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
        log.info("sendMessageForDriver: message successfully send to Kafka topic. Message: {}", message);
        kafkaTemplate.send(driverTopic, message);
    }

    public void sendMessageForPassenger(Long passengerId, BigDecimal rating) {

        UpdateRatingRequest message = UpdateRatingRequest.builder()
            .id(passengerId)
            .rating(rating)
            .build();
        log.info("sendMessageForPassenger: message successfully send to Kafka topic. Message: {}", message);
        kafkaTemplate.send(passengerTopic, message);
    }
}
