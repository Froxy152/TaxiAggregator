package by.shestakov.passengerservice.controller.impl;

import static by.shestakov.passengerservice.constant.UnitTestConstants.TEST_ID;
import static by.shestakov.passengerservice.constant.UnitTestConstants.alreadyPassengerRequest;
import static by.shestakov.passengerservice.constant.UnitTestConstants.defaultRequest;
import static by.shestakov.passengerservice.constant.UnitTestConstants.invalidEmailRequest;
import static by.shestakov.passengerservice.constant.UnitTestConstants.invalidPhoneNumberRequest;
import static by.shestakov.passengerservice.constant.UnitTestConstants.updateAlreadyEmailPassengerRequest;
import static by.shestakov.passengerservice.constant.UnitTestConstants.updateAlreadyNumberPassengerRequest;
import static by.shestakov.passengerservice.constant.UnitTestConstants.updatePassengerRequest;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertTrue;

import by.shestakov.passengerservice.PassengerServiceApplication;
import by.shestakov.passengerservice.dto.request.PassengerRequest;
import by.shestakov.passengerservice.dto.request.UpdatePassengerRequest;
import by.shestakov.passengerservice.entity.Passenger;
import by.shestakov.passengerservice.repository.PassengerRepository;
import by.shestakov.passengerservice.util.ExceptionConstants;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

@Testcontainers
@SpringBootTest(classes = PassengerServiceApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PassengerControllerImplIt {

    @Container
    static PostgreSQLContainer psqlContainer = new PostgreSQLContainer(DockerImageName.parse("postgres:latest"));

    @Autowired
    private PassengerRepository passengerRepository;

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", psqlContainer::getJdbcUrl);
        registry.add("spring.datasource.username", psqlContainer::getUsername);
        registry.add("spring.datasource.password", psqlContainer::getPassword);
    }

    @LocalServerPort
    private Integer port;


    @BeforeEach
    void setup() {
        RestAssured.baseURI = "http://localhost:" + this.port + "/api/v1/passengers";
    }

    @Test
    void getAll_200() {
        given()
                .queryParam("offset", 0)
                .queryParam("limit", 5)
                .when()
                .get()
                .then()
                .contentType(ContentType.JSON)
                .statusCode(200)
                .body("values[0].id", notNullValue())
                .body("values[0].name", notNullValue())
                .body("values[0].lastName", notNullValue())
                .body("values[0].email", notNullValue())
                .body("values[0].phoneNumber", notNullValue())
                .body("values[0].rating", notNullValue())
                .body("values[0].isDeleted", notNullValue());
    }

    @Test
    void getById_200() {
        Long id = TEST_ID;

        given()
                .when()
                .get("/{id}", id)
                .then()
                .contentType(ContentType.JSON)
                .statusCode(200)
                .body("id", equalTo(1));
    }

    @Test
    void getById_PassengerNotFound_404() {
        Long id = 999L;

        given()
                .when()
                .get("/{id}", id)
                .then()
                .contentType(ContentType.JSON)
                .statusCode(404)
                .body("errors.message", equalTo(ExceptionConstants.NOT_FOUND_MESSAGE.formatted(id)));
    }

    @Test
    void create_201() {
        PassengerRequest request = defaultRequest();
        given()
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .post()
                .then()
                .statusCode(201)
                .contentType(ContentType.JSON)
                .body("email", equalTo(request.email()));
    }

    @Test
    void create_InvalidEmailRequest_400() {
        PassengerRequest request = invalidEmailRequest();
        given()
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .post()
                .then()
                .statusCode(400)
                .contentType(ContentType.JSON);
    }

    @Test
    void create_InvalidPhoneNumberRequest_400() {
        PassengerRequest request = invalidPhoneNumberRequest();
        given()
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .post()
                .then()
                .statusCode(400)
                .contentType(ContentType.JSON);
    }

    @Test
    void create_PassengerCredentialsAlreadyExists_409() {
        PassengerRequest request = alreadyPassengerRequest();

        given()
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .post()
                .then()
                .statusCode(409)
                .contentType(ContentType.JSON)
                .body("errors.message",
                        equalTo(ExceptionConstants.CONFLICT_MESSAGE.formatted(request.email(), request.phoneNumber())));
    }

    @Test
    void update_200() {
        UpdatePassengerRequest request = updatePassengerRequest();
        Long id = TEST_ID;

        given()
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .put("/{id}", id)
                .then()
                .contentType(ContentType.JSON)
                .statusCode(200)
                .body("email", equalTo(request.email()));
    }

    @Test
    void update_PassengerNotFound_404() {
        UpdatePassengerRequest request = updatePassengerRequest();
        Long id = 999L;

        given()
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .put("/{id}", id)
                .then()
                .contentType(ContentType.JSON)
                .statusCode(404)
                .body("errors.message", equalTo(ExceptionConstants.NOT_FOUND_MESSAGE.formatted(id)));
    }

    @Test
    void update_PassengerEmailExists_409() {
        UpdatePassengerRequest request = updateAlreadyEmailPassengerRequest();
        Long id = TEST_ID;

        given()
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .put("/{id}", id)
                .then()
                .contentType(ContentType.JSON)
                .statusCode(409)
                .body("errors.message",
                        equalTo(ExceptionConstants.CONFLICT_DATA_ALREADY_REGISTERED.formatted(request.email())));
    }

    @Test
    void update_PassengerPhoneNumberExists_409() {
        UpdatePassengerRequest request = updateAlreadyNumberPassengerRequest();
        Long id = TEST_ID;

        given()
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .put("/{id}", id)
                .then()
                .contentType(ContentType.JSON)
                .statusCode(409)
                .body("errors.message",
                        equalTo(ExceptionConstants.CONFLICT_DATA_ALREADY_REGISTERED.formatted(request.phoneNumber())));
    }

    @Test
    void delete_204() {
        Long id = TEST_ID;

        given()
                .when()
                .delete("/{id}", id)
                .then()
                .statusCode(204);

        Passenger deletePassenger = passengerRepository.findById(TEST_ID).orElseThrow();
        assertTrue(deletePassenger.getIsDeleted());
    }

    @Test
    void delete_404() {
        Long id = 999L;

        given()
                .when()
                .delete("/{id}", id)
                .then()
                .contentType(ContentType.JSON)
                .statusCode(404)
                .body("errors.message", equalTo(ExceptionConstants.NOT_FOUND_MESSAGE.formatted(id)));
    }
}