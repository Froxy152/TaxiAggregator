package by.shestakov.ratingservice.controller.impl;

import by.shestakov.ratingservice.config.WireMockConfiguration;
import static by.shestakov.ratingservice.constant.TestConstant.DEFAULT_ADDRESS;
import static by.shestakov.ratingservice.constant.TestConstant.TEST_COMMENTARY_DTO;
import static by.shestakov.ratingservice.constant.TestConstant.TEST_DRIVER_ID;
import static by.shestakov.ratingservice.constant.TestConstant.TEST_DRIVER_INVALID_ID;
import static by.shestakov.ratingservice.constant.TestConstant.TEST_ID;
import static by.shestakov.ratingservice.constant.TestConstant.TEST_INVALID_ID;
import static by.shestakov.ratingservice.constant.TestConstant.defaultRatingByPassengerForInsert;
import static by.shestakov.ratingservice.constant.TestConstant.defaultRatingDriverRequest;
import static by.shestakov.ratingservice.constant.TestConstant.defaultRatingDriverRequestInvalidDriverIDForIT;
import static by.shestakov.ratingservice.constant.TestConstant.defaultRatingDriverRequestInvalidPassengerIDForIT;
import static by.shestakov.ratingservice.constant.TestConstant.defaultRatingDriverRequestInvalidRideID;
import static by.shestakov.ratingservice.constant.TestConstant.defaultRatingDriverResponse;
import by.shestakov.ratingservice.dto.request.CommentaryDto;
import by.shestakov.ratingservice.dto.request.RatingRequest;
import by.shestakov.ratingservice.dto.response.RatingResponse;
import com.github.tomakehurst.wiremock.WireMockServer;
import io.restassured.RestAssured;
import static io.restassured.RestAssured.given;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.context.EmbeddedKafka;
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
@EmbeddedKafka
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class RatingControllerImplIntegrationTest {

    @Container
    static final MongoDBContainer mongoDBContainer = new MongoDBContainer(
            DockerImageName.parse("mongo:latest"));

    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.data.mongodb.uri", mongoDBContainer::getConnectionString);
    }

    @LocalServerPort
    private Integer port;

    @Autowired
    private EmbeddedKafkaBroker kafkaBroker;

    @Autowired
    private WireMockServer wireMockServer;

    @Autowired
    private MongoTemplate mongoTemplate;

    @BeforeEach
    void setUp() {
        RestAssured.baseURI = "http://localhost:" + this.port + DEFAULT_ADDRESS;
        mongoTemplate.dropCollection("reviews");
        mongoTemplate.insert(defaultRatingByPassengerForInsert());
    }

    @Test
    void getAllRatings() {
        int offset = 0;
        int limit = 1;

        given()
                .queryParam("offset", String.valueOf(offset))
                .queryParam("limit", String.valueOf(limit))
                .when()
                .get()
                .then()
                .contentType(ContentType.JSON)
                .statusCode(200)
                .body("values[0].rideId", notNullValue());
    }

    @Test
    void getAverageRating() throws Exception{
        int limit = 1;
        Long driverId = TEST_DRIVER_ID;

        WireMockConfiguration.getDriverMock(wireMockServer);

        given()
                .queryParam("limit", String.valueOf(limit))
                .when()
                .get("/{driverId}", driverId)
                .then()
                .contentType(ContentType.JSON)
                .statusCode(200);
    }

    @Test
    void getAverageRating_DriverNotFound_() throws Exception {
        int limit = 1;
        Long driverId = TEST_DRIVER_INVALID_ID;

        WireMockConfiguration.getDriverNotFoundMock(wireMockServer);

        given()
                .queryParam("limit", String.valueOf(limit))
                .when()
                .get("/{driverId}", driverId)
                .then()
                .contentType(ContentType.JSON)
                .statusCode(404);
    }

    @Test
    void createNewReview_201() throws Exception {
        WireMockConfiguration.getDriverMock(wireMockServer);
        WireMockConfiguration.getPassengerMock(wireMockServer);
        WireMockConfiguration.getRide(wireMockServer);

        RatingRequest request = defaultRatingDriverRequest();

        RatingResponse expectedResponse = defaultRatingDriverResponse();

        Response response = given()
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .post();

        response
                .then()
                .contentType(ContentType.JSON)
                .statusCode(201);

        RatingResponse ratingResponse = response.as(RatingResponse.class);

        assertEquals(expectedResponse.ratedBy(), ratingResponse.ratedBy() );
    }

    @Test
    void createNewReview_DriverInvalidId_404() throws Exception {
        WireMockConfiguration.getRide(wireMockServer);
        WireMockConfiguration.getDriverNotFoundMock(wireMockServer);

        RatingRequest request = defaultRatingDriverRequestInvalidDriverIDForIT();

        given()
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .post()
                .then()
                .contentType(ContentType.JSON)
                .statusCode(404);
    }

    @Test
    void createNewReview_RideInvalidId_404() throws Exception {
        WireMockConfiguration.getRideNotFound(wireMockServer);

        RatingRequest request = defaultRatingDriverRequestInvalidRideID();

        given()
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .post()
                .then()
                .contentType(ContentType.JSON)
                .statusCode(404);
    }

    @Test
    void createNewReview_PassengerInvalidId_404() throws Exception {
        WireMockConfiguration.getRide(wireMockServer);
        WireMockConfiguration.getDriverMock(wireMockServer);
        WireMockConfiguration.getPassengerNotFoundMock(wireMockServer);

        RatingRequest request = defaultRatingDriverRequestInvalidPassengerIDForIT();

        given()
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .post()
                .then()
                .contentType(ContentType.JSON)
                .statusCode(404);
    }

    @Test
    void updateCommentUnderReview_200() {
        String id = TEST_ID;
        CommentaryDto request = TEST_COMMENTARY_DTO;

        given()
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .patch("/{id}", id)
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("commentary", equalTo(request.text()));
    }

    @Test
    void updateCommentUnderReview_ReviewNotFound_404() {
        String id = TEST_INVALID_ID;
        CommentaryDto request = TEST_COMMENTARY_DTO;

        given()
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .patch("/{id}", id)
                .then()
                .statusCode(404)
                .contentType(ContentType.JSON);
    }

}