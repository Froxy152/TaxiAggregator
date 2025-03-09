package by.shestakov.driverservice.controller.impl;

import static by.shestakov.constant.TestCarData.TEST_CAR_ID;
import static by.shestakov.constant.TestCarData.TEST_DRIVER_ID;
import static by.shestakov.constant.TestCarData.alreadyCarRequest;
import static by.shestakov.constant.TestCarData.alreadyUpdateRequest;
import static by.shestakov.constant.TestCarData.defaultCarRequest;
import static by.shestakov.constant.TestCarData.updateRequest;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertTrue;

import by.shestakov.driverservice.dto.request.CarRequest;
import by.shestakov.driverservice.dto.request.CarUpdateRequest;
import by.shestakov.driverservice.entity.Car;
import by.shestakov.driverservice.repository.CarRepository;
import by.shestakov.driverservice.util.ExceptionMessages;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.annotation.Order;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

@ActiveProfiles("test")
@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CarControllerImplIT {

    @Container
    static PostgreSQLContainer postgreSQLContainer = new PostgreSQLContainer<>(
            DockerImageName.parse("postgres:latest"));

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
        registry.add("spring.datasource.username", postgreSQLContainer::getUsername);
        registry.add("spring.datasource.password", postgreSQLContainer::getPassword);
    }

    @BeforeEach
    void setup() {
        RestAssured.baseURI = "http://localhost:" + this.port + "/api/v1/cars";
    }

    @LocalServerPort
    private Integer port;

    @Autowired
    private CarRepository carRepository;

    @Test
    @Order(1)
    void getAllCars() {
        given()
                .when()
                .queryParam("offset", 0)
                .queryParam("limit", 3)
                .get()
                .then()
                .contentType(ContentType.JSON)
                .statusCode(200)
                .body("values[0].id", notNullValue())
                .body("values[0].carBrand", notNullValue())
                .body("values[0].carNumber", notNullValue())
                .body("values[0].carColor", notNullValue())
                .body("values[0].isDeleted", notNullValue());
    }

    @DirtiesContext
    @Test
    @Order(2)
    void createCar() {
        CarRequest request = defaultCarRequest();
        Long driverId = TEST_DRIVER_ID;

        given()
                .contentType(ContentType.JSON)
                .body(request)
                .queryParam("driverId", driverId)
                .when()
                .post()
                .then()
                .contentType(ContentType.JSON)
                .statusCode(201)
                .body("carNumber", equalTo(request.carNumber()))
                .body("carBrand", equalTo(request.carBrand()))
                .body("carColor", equalTo(request.carColor()));
    }

    @Test
    @Order(3)
    void createCar_CarNumberExists_409() {
        CarRequest request = alreadyCarRequest();
        Long driverId = TEST_DRIVER_ID;

        given()
                .contentType(ContentType.JSON)
                .body(request)
                .queryParam("driverId", driverId)
                .when()
                .post()
                .then()
                .contentType(ContentType.JSON)
                .statusCode(409)
                .body("errors.message", equalTo(ExceptionMessages.CONFLICT_MESSAGE.formatted("car")));
    }

    @Test
    @Order(4)
    void createCar_CarDriverNotFound_404() {
        CarRequest request = defaultCarRequest();
        Long driverId = 999L;

        given()
                .contentType(ContentType.JSON)
                .body(request)
                .queryParam("driverId", driverId)
                .when()
                .post()
                .then()
                .contentType(ContentType.JSON)
                .statusCode(404)
                .body("errors.message", equalTo(ExceptionMessages.NOT_FOUND_MESSAGE.formatted("driver", driverId)));
    }

    @DirtiesContext
    @Test
    @Order(5)
    void updateCar_200() {
        CarUpdateRequest request = updateRequest();
        Long id = TEST_CAR_ID;

        given()
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .put("/{id}", id)
                .then()
                .contentType(ContentType.JSON)
                .statusCode(200)
                .body("carNumber", equalTo(request.carNumber()))
                .body("carBrand", equalTo(request.carBrand()))
                .body("carColor", equalTo(request.carColor()));

    }

    @Test
    @Order(6)
    void updateCar_CarNotFound_404() {
        CarUpdateRequest request = updateRequest();
        Long id = 999L;

        given()
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .put("/{id}", id)
                .then()
                .contentType(ContentType.JSON)
                .statusCode(404)
                .body("errors.message", equalTo(ExceptionMessages.NOT_FOUND_MESSAGE.formatted("car", id)));
    }

    @DirtiesContext
    @Test
    @Order(7)
    void updateCar_CarNumberAlreadyExists_409() {
        CarUpdateRequest request = alreadyUpdateRequest();
        Long id = TEST_CAR_ID;

        given()
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .put("/{id}", id)
                .then()
                .contentType(ContentType.JSON)
                .statusCode(409)
                .body("errors.message", equalTo(ExceptionMessages.CONFLICT_MESSAGE.formatted("car")));
    }

    @DirtiesContext
    @Test
    @Order(8)
    void deleteCar_204() {
        Long id = 2L;

        given()
                .when()
                .delete("/{id}", id)
                .then()
                .statusCode(204);

        Car car = carRepository.findById(id).orElseThrow();
        assertTrue(car.getIsDeleted());
    }

    @DirtiesContext
    @Test
    @Order(9)
    void deleteCar_CarNotFound_404() {
        Long id = 999L;

        given()
                .when()
                .delete("/{id}", id)
                .then()
                .statusCode(404)
                .body("errors.message", equalTo(ExceptionMessages.NOT_FOUND_MESSAGE.formatted("car", id)));
    }
}