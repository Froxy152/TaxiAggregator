package by.shestakov.ridesservice.controller.impl;

import static testConstant.TestConstant.TEST_DRIVER_RESPONSE;
import static testConstant.TestConstant.TEST_DRIVER_RESPONSE_WITHOUT_CAR;
import static testConstant.TestConstant.TEST_ID;
import static testConstant.TestConstant.TEST_PASSENGER_RESPONSE;
import static testConstant.TestConstant.TEST_PICKUP_ADDRESS;
import static testConstant.TestConstant.TEST_ROUTING_RESPONSE;
import static testConstant.TestConstant.defaultRide;
import static testConstant.TestConstant.defaultRideDriverNotFoundRequest;
import static testConstant.TestConstant.defaultRidePassengerNotFoundRequest;
import static testConstant.TestConstant.defaultRideRequest;
import static testConstant.TestConstant.defaultRideStatusRequest;
import static testConstant.TestConstant.defaultRideUpdateRequest;
import static testConstant.TestConstant.invalidRequest;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

import by.shestakov.ridesservice.dto.request.RideRequest;
import by.shestakov.ridesservice.dto.request.RideStatusRequest;
import by.shestakov.ridesservice.dto.request.RideUpdateRequest;
import by.shestakov.ridesservice.repository.RideRepository;
import com.github.tomakehurst.wiremock.WireMockServer;
import config.WireMockConfiguration;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
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
import wiremock.com.fasterxml.jackson.databind.ObjectMapper;

@ActiveProfiles("test")
@Testcontainers
@AutoConfigureWireMock(port = 8090)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class RideControllerImplIT {

    @Container
    static final MongoDBContainer mongoDBContainer = new MongoDBContainer(
            DockerImageName.parse("mongo:latest"));

    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.data.mongodb.uri", mongoDBContainer::getConnectionString);
    }

    @Autowired
    private RideRepository rideRepository;

    @BeforeEach
    void setup() {
        RestAssured.baseURI = "http://localhost:" + this.port + "/api/v1/rides";
    }

    @LocalServerPort
    private Integer port;

    @Autowired
    private WireMockServer wireMockServer;

    @Autowired
    private MongoTemplate mongoTemplate;


    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        mongoTemplate.dropCollection("rides");
        mongoTemplate.insert(defaultRide());
    }

    @Test
    void getAll() {
        int offset = 0;
        int limit = 2;

        given()
                .queryParam("offset", String.valueOf(offset))
                .queryParam("limit", String.valueOf(limit))
                .when()
                .get()
                .then()
                .contentType(ContentType.JSON)
                .statusCode(200)
                .body("values[0].pickUpAddress", notNullValue())
                .body("values[0].destinationAddress", notNullValue());
    }


    @Test
    void getById_200() {
        given()
                .when()
                .get("/{id}", TEST_ID)
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("pickUpAddress", equalTo(TEST_PICKUP_ADDRESS));
    }

    @Test
    void getById_404() {
        given()
                .when()
                .get("/{id}", "ASDASDASD")
                .then()
                .statusCode(404)
                .contentType(ContentType.JSON);
    }

    @Test
    void create() throws Exception {
        WireMockConfiguration.getDriverMock(wireMockServer, objectMapper, TEST_DRIVER_RESPONSE);
        WireMockConfiguration.getPassengerMock(wireMockServer, objectMapper, TEST_PASSENGER_RESPONSE);
        WireMockConfiguration.getRouteMock(wireMockServer, objectMapper, TEST_ROUTING_RESPONSE);

        RideRequest request = defaultRideRequest();

        given()
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .post()
                .then()
                .contentType(ContentType.JSON)
                .statusCode(201);
    }

    @Test
    void create_DriverNotFound_404() throws Exception {
        WireMockConfiguration.getDriverNotFoundMock(wireMockServer);

        RideRequest request = defaultRideDriverNotFoundRequest();

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
    void create_PassengerNotFound_404() throws Exception {
        WireMockConfiguration.getDriverMock(wireMockServer, objectMapper, TEST_DRIVER_RESPONSE);
        WireMockConfiguration.getPassengerNotFoundMock(wireMockServer);

        RideRequest request = defaultRidePassengerNotFoundRequest();

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
    void create_DriverWithoutCar_400() throws Exception {
        WireMockConfiguration.getDriverMock(wireMockServer, objectMapper, TEST_DRIVER_RESPONSE_WITHOUT_CAR);
        RideRequest request = defaultRideRequest();

        given()
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .post()
                .then()
                .contentType(ContentType.JSON)
                .statusCode(400);
    }

    @Test
    void create_BadRequest_400() throws Exception {
        WireMockConfiguration.getDriverMock(wireMockServer, objectMapper, TEST_DRIVER_RESPONSE_WITHOUT_CAR);
        RideRequest request = invalidRequest();

        given()
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .post()
                .then()
                .contentType(ContentType.JSON)
                .statusCode(400);
    }

    @Test
    void updateStatus_200() {
        String id = TEST_ID;
        RideStatusRequest request = defaultRideStatusRequest();

        given()
                .queryParam("rideId", id)
                .body(request)
                .contentType(ContentType.JSON)
                .when()
                .patch()
                .then()
                .contentType(ContentType.JSON)
                .statusCode(200)
                .body("status", equalTo(request.status().toString()));
    }

    @Test
    void updateStatus_404() {
        String id = "ASDASDAD";
        RideStatusRequest request = defaultRideStatusRequest();

        given()
                .queryParam("rideId", id)
                .body(request)
                .contentType(ContentType.JSON)
                .when()
                .patch()
                .then()
                .contentType(ContentType.JSON)
                .statusCode(404);
    }

    @Test
    void updateRide() throws Exception {
        WireMockConfiguration.getRouteMock(wireMockServer, objectMapper, TEST_ROUTING_RESPONSE);
        RideUpdateRequest request = defaultRideUpdateRequest();
        String id = TEST_ID;

        given()
                .contentType(ContentType.JSON)
                .body(request)
                .queryParam("rideId", id)
                .when()
                .put()
                .then()
                .contentType(ContentType.JSON)
                .statusCode(200)
                .body("pickUpAddress", equalTo(request.pickUpAddress()));
    }

    @Test
    void updateRide_404() throws Exception {
        RideUpdateRequest request = defaultRideUpdateRequest();
        String id = "ASDSAD";

        given()
                .contentType(ContentType.JSON)
                .body(request)
                .queryParam("rideId", id)
                .when()
                .put()
                .then()
                .contentType(ContentType.JSON)
                .statusCode(404);
    }
}