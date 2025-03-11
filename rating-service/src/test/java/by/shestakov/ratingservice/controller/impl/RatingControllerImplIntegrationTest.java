package by.shestakov.ratingservice.controller.impl;

import by.shestakov.ratingservice.config.KafkaConfig;
import by.shestakov.ratingservice.config.WireMockConfiguration;
import static by.shestakov.ratingservice.constant.TestConstant.defaultRatingByPassengerForInsert;
import static by.shestakov.ratingservice.constant.TestConstant.defaultRatingDriverRequest;
import by.shestakov.ratingservice.dto.request.RatingRequest;
import by.shestakov.ratingservice.kafka.KafkaProducer;
import com.github.tomakehurst.wiremock.WireMockServer;
import io.restassured.RestAssured;
import static io.restassured.RestAssured.given;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

@ActiveProfiles("test")
@AutoConfigureWireMock(port = 8090)
@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class RatingControllerImplIntegrationTest {

    @Container
    static final MongoDBContainer mongoDBContainer = new MongoDBContainer(
            DockerImageName.parse("mongo:latest"));

    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.data.mongodb.uri", mongoDBContainer::getConnectionString);
    }

    @BeforeEach
    void setup() {
        RestAssured.baseURI = "http://localhost:" + this.port + "/api/v1/ratings";
    }

    @Mock
    private KafkaConfig kafkaConfig;

    @Mock
    private KafkaProducer kafkaProducer;

    @LocalServerPort
    private Integer port;

    @Autowired
    private WireMockServer wireMockServer;

    @Autowired
    private MongoTemplate mongoTemplate;

    @BeforeEach
    void setUp() {
        mongoTemplate.dropCollection("reviews");
        mongoTemplate.insert(defaultRatingByPassengerForInsert());
    }

    @Test
    void getAllRatings() {
    }

    @Test
    void getAverageRating() {

    }

    @Test
    void createNewReview() throws Exception {
        WireMockConfiguration.getDriverMock(wireMockServer);
        WireMockConfiguration.getPassengerMock(wireMockServer);
        WireMockConfiguration.getRide(wireMockServer);

        RatingRequest request = defaultRatingDriverRequest();

        given()
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .post()
                .then()
                .contentType(ContentType.JSON)
                .statusCode(200);
    }


}