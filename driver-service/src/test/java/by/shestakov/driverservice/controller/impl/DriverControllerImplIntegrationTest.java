package by.shestakov.driverservice.controller.impl;

import static by.shestakov.constant.TestDriverData.DEFAULT_DRIVER_ADDRESS;
import static by.shestakov.constant.TestDriverData.INVALID_ID;
import static by.shestakov.constant.TestDriverData.TEST_ID;
import static by.shestakov.constant.TestDriverData.alReadyDriverUpdateRequest;
import static by.shestakov.constant.TestDriverData.alreadyEmailDriverRequest;
import static by.shestakov.constant.TestDriverData.alreadyNumberDriverRequest;
import static by.shestakov.constant.TestDriverData.defaultDriverRequest;
import static by.shestakov.constant.TestDriverData.driverUpdateRequest;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertTrue;

import by.shestakov.configuration.PostgresContainer;
import by.shestakov.driverservice.dto.request.DriverRequest;
import by.shestakov.driverservice.dto.request.DriverUpdateRequest;
import by.shestakov.driverservice.entity.Driver;
import by.shestakov.driverservice.repository.DriverRepository;
import by.shestakov.driverservice.util.ExceptionMessages;
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
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class DriverControllerImplIntegrationTest extends PostgresContainer {

    @Autowired
    private DriverRepository repository;

    @BeforeEach
    void setup() {
        RestAssured.baseURI = "http://localhost:" + this.port + DEFAULT_DRIVER_ADDRESS;
    }

    @LocalServerPort
    private Integer port;

    @Order(1)
    @Test
    void getAll() {
        given()
                .when()
                .queryParam("offset", 0)
                .queryParam("limit", 3)
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

    @Order(2)
    @Test
    void getById_200() {
        Long id = TEST_ID;

        given()
                .when()
                .get("/{id}", id)
                .then()
                .contentType(ContentType.JSON)
                .statusCode(200)
                .body("name", equalTo("Иван"));
    }

    @Order(3)
    @Test
    void getById_DriverNotFound_404() {
        Long id = INVALID_ID;

        given()
                .when()
                .get("/{id}", id)
                .then()
                .contentType(ContentType.JSON)
                .statusCode(404)
                .body("errors.message", equalTo(ExceptionMessages.NOT_FOUND_MESSAGE.formatted("driver", id)));
    }

    @Order(4)
    @Test
    void create_201() {
        DriverRequest request = defaultDriverRequest();

        given()
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .post()
                .then()
                .contentType(ContentType.JSON)
                .statusCode(201)
                .body("phoneNumber", equalTo(request.phoneNumber()))
                .body("email", equalTo(request.email()));
    }

    @Order(5)
    @Test
    void create_DriversPhoneNumberAlreadyExists_409() {
        DriverRequest request = alreadyNumberDriverRequest();

        given()
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .post()
                .then()
                .contentType(ContentType.JSON)
                .statusCode(409)
                .body("errors.message", equalTo(ExceptionMessages.CONFLICT_MESSAGE.formatted("driver")));
    }

    @Order(6)
    @Test
    void create_DriversEmailAlreadyExists_409() {
        DriverRequest request = alreadyEmailDriverRequest();

        given()
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .post()
                .then()
                .contentType(ContentType.JSON)
                .statusCode(409)
                .body("errors.message", equalTo(ExceptionMessages.CONFLICT_MESSAGE.formatted("driver")));
    }

    @Order(7)
    @Test
    void updateDriver() {
        DriverUpdateRequest request = driverUpdateRequest();
        Long id = TEST_ID;

        given()
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .put("/{id}", id)
                .then()
                .contentType(ContentType.JSON)
                .statusCode(200)
                .body("email", equalTo(request.email()))
                .body("phoneNumber", equalTo(request.phoneNumber()));
    }

    @Order(8)
    @Test
    void updateDriver_DriverNotFound_404() {
        DriverUpdateRequest request = driverUpdateRequest();
        Long id = INVALID_ID;

        given()
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .put("/{id}", id)
                .then()
                .contentType(ContentType.JSON)
                .statusCode(404)
                .body("errors.message", equalTo(ExceptionMessages.NOT_FOUND_MESSAGE.formatted("driver", id)));
    }

    @Order(9)
    @Test
    void updateDriver_DriverEmailOrPhoneAlreadyExists_409() {
        DriverUpdateRequest request = alReadyDriverUpdateRequest();
        Long id = TEST_ID;

        given()
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .put("/{id}", id)
                .then()
                .contentType(ContentType.JSON)
                .statusCode(409)
                .body("errors.message", equalTo(ExceptionMessages.CONFLICT_MESSAGE.formatted("driver")));
    }

    @Order(10)
    @Test
    void deleteDriver_204() {
        Long id = TEST_ID;

        given()
                .when()
                .delete("/{id}", id)
                .then()
                .statusCode(204);

        Driver driver = repository.findById(id).orElseThrow();
        assertTrue(driver.getIsDeleted());
    }

    @Order(11)
    @Test
    void deleteDriver_DriverNotFound() {
        Long id = INVALID_ID;

        given()
                .when()
                .delete("/{id}", id)
                .then()
                .statusCode(404)
                .body("errors.message", equalTo(ExceptionMessages.NOT_FOUND_MESSAGE.formatted("driver", id)));
    }
}