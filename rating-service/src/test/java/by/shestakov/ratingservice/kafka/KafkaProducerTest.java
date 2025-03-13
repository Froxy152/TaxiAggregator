package by.shestakov.ratingservice.kafka;

import static by.shestakov.ratingservice.constant.TestConstant.TEST_DRIVER_ID;
import static by.shestakov.ratingservice.constant.TestConstant.TEST_PASSENGER_ID;
import static by.shestakov.ratingservice.constant.TestConstant.TEST_RATE;
import static by.shestakov.ratingservice.constant.TestConstant.defaultConsumer;
import by.shestakov.ratingservice.dto.request.UpdateRatingRequest;
import java.time.Duration;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.test.context.EmbeddedKafka;

@EmbeddedKafka(partitions = 1, brokerProperties = {"listeners=PLAINTEXT://localhost:9092", "port=9092"})
@SpringBootTest
class KafkaProducerTest {

    @Autowired
    private KafkaProducer kafkaProducer;

    @Test
    void sendMessageForDriver() {
        UpdateRatingRequest request = UpdateRatingRequest.builder()
                .id(TEST_DRIVER_ID)
                .rating(TEST_RATE)
                .build();

        KafkaConsumer<String, UpdateRatingRequest> consumer = defaultConsumer();

        kafkaProducer.sendMessageForDriver(TEST_DRIVER_ID, TEST_RATE);

        ConsumerRecords<String, UpdateRatingRequest> records = consumer.poll(Duration.ofMillis(10000L));
        consumer.close();

        assertEquals(1, records.count());
        assertEquals(request.id(), records.iterator().next().value().id());
        assertEquals(request.rating(), records.iterator().next().value().rating());
    }

    @Test
    void sendMessageForPassenger() {
        UpdateRatingRequest request = UpdateRatingRequest.builder()
                .id(TEST_PASSENGER_ID)
                .rating(TEST_RATE)
                .build();

        KafkaConsumer<String, UpdateRatingRequest> consumer = defaultConsumer();

        kafkaProducer.sendMessageForDriver(TEST_DRIVER_ID, TEST_RATE);

        ConsumerRecords<String, UpdateRatingRequest> records = consumer.poll(Duration.ofMillis(10000L));
        consumer.close();

        assertEquals(1, records.count());
        assertEquals(request.id(), records.iterator().next().value().id());
        assertEquals(request.rating(), records.iterator().next().value().rating());
    }
}