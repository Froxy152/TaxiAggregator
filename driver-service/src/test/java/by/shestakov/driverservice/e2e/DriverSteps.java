package by.shestakov.driverservice.e2e;

import static by.shestakov.constant.TestCarData.DEFAULT_CAR_ADDRESS;
import static by.shestakov.constant.TestCarData.defaultCarRequest;
import static by.shestakov.constant.TestCarData.defaultCarResponse;
import static by.shestakov.constant.TestCarData.updateRequest;
import static by.shestakov.constant.TestDriverData.DEFAULT_DRIVER_ADDRESS;
import static by.shestakov.constant.TestDriverData.alReadyDriverUpdateRequest;
import static by.shestakov.constant.TestDriverData.alreadyEmailDriverRequest;
import static by.shestakov.constant.TestDriverData.alreadyNumberDriverRequest;
import static by.shestakov.constant.TestDriverData.defaultDriverRequest;
import static by.shestakov.constant.TestDriverData.defaultDriverResponse;
import static by.shestakov.constant.TestDriverData.driverUpdateRequest;
import static by.shestakov.constant.TestDriverData.getFirstDriver;
import by.shestakov.driverservice.dto.request.CarRequest;
import by.shestakov.driverservice.dto.request.CarUpdateRequest;
import by.shestakov.driverservice.dto.request.DriverRequest;
import by.shestakov.driverservice.dto.request.DriverUpdateRequest;
import by.shestakov.driverservice.dto.response.CarResponse;
import by.shestakov.driverservice.dto.response.DriverResponse;
import by.shestakov.driverservice.util.ExceptionMessages;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import static io.restassured.RestAssured.given;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class DriverSteps {

    private DriverRequest request;
    private DriverUpdateRequest updateRequest;
    private Response response;
    private CarRequest carRequest;
    private CarUpdateRequest carUpdateRequest;


    @Given("I have a Driver")
    public void i_have_a_driver() {
        request = defaultDriverRequest();
    }

    @When("I send a POST driver request")
    public void i_send_a_post_driver_request() {
        response = given()
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .post(DEFAULT_DRIVER_ADDRESS);
    }

    @Then("The response should be {int}")
    public void the_response_should_be(int status) {
        response
                .then()
                .contentType(ContentType.JSON)
                .statusCode(status);
    }

    @Given("I have a Driver with exists number")
    public void i_have_a_driver_with_exists_number() {
        request = alreadyNumberDriverRequest();
    }

    @Given("I have a Driver with exists email")
    public void i_have_a_driver_with_exists_email() {
        request = alreadyEmailDriverRequest();
    }

    @Given("I have updated driver request")
    public void i_have_updated_driver_request() {
        updateRequest = driverUpdateRequest();
    }

    @When("I send a PUT driver request with {int}")
    public void i_send_a_put_driver_request_with(int id) {
        response = given()
                .contentType(ContentType.JSON)
                .body(updateRequest)
                .when()
                .put(DEFAULT_DRIVER_ADDRESS + "/{id}", id);
    }

    @Given("I have already exists updated driver request")
    public void i_have_already_exists_updated_driver_request() {
        updateRequest = alReadyDriverUpdateRequest();
    }

    @When("I send a DELETE driver request with {int}")
    public void i_send_a_delete_driver_request_with(int id) {
        response = given()
                .when()
                .delete(DEFAULT_DRIVER_ADDRESS + "/{id}", id);
    }

    @Then("The response should be deleted {int}")
    public void the_response_should_be_deleted(int status) {
        response
                .then()
                .statusCode(status);
    }

    @And("valid response check")
    public void valid_response_check() {
        DriverResponse driverResponse = response.as(DriverResponse.class);

        assertEquals(defaultDriverResponse(), driverResponse);
    }

    @And("Body driver should be conflict")
    public void body_driver_should_be_conflict() {
        response
                .then()
                .body("errors.message", equalTo(ExceptionMessages.CONFLICT_MESSAGE.formatted("driver")));
    }

    @And("Body driver should be not found {int}")
    public void body_should_be_not_found(int id) {
        response
                .then()
                .body("errors.message", equalTo(ExceptionMessages.NOT_FOUND_MESSAGE.formatted("driver", id)));
    }

    @When("I send a GET request {int}")
    public void i_send_a_get_request(int id) {
        response = given()
                .when()
                .get(DEFAULT_DRIVER_ADDRESS + "/{id}", id);
    }

    @And("valid get response check")
    public void valid_get_response_check() {
        DriverResponse driverResponse = response.as(DriverResponse.class);

        assertEquals(getFirstDriver(), driverResponse);
    }

    @When("I send a GET driver request offset {int} limit {int}")
    public void i_send_a_get_driver_request_offset_limit(int offset, int limit) {
        response = given()
                .queryParam("offset", String.valueOf(offset))
                .queryParam("limit", String.valueOf(limit))
                .when()
                .get(DEFAULT_DRIVER_ADDRESS);
    }

    @When("I send a GET request with offset {int} limit {int}")
    public void i_send_a_get_request_with_offset_limit(int offset, int limit) {
        response = given()
                .queryParam("offset", String.valueOf(offset))
                .queryParam("limit", String.valueOf(limit))
                .when()
                .get(DEFAULT_CAR_ADDRESS);
    }

    @Given("I have a car")
    public void i_have_a_car() {
        carRequest = defaultCarRequest();
    }

    @When("I send a POST car request with {int}")
    public void i_send_a_post_car_request_with(int id) {
        response = given()
                .contentType(ContentType.JSON)
                .body(carRequest)
                .queryParam("driverId", String.valueOf(id))
                .when()
                .post(DEFAULT_CAR_ADDRESS);
    }

    @And("validate car response")
    public void validate_car_response() {
        CarResponse carResponse = response.as(CarResponse.class);

        assertEquals(defaultCarResponse(), carResponse);
    }

    @And("Body car should be conflict")
    public void body_car_should_be_conflict() {
        response
                .then()
                .body("errors.message", equalTo(ExceptionMessages.CONFLICT_MESSAGE.formatted("car")));
    }

    @And("Body car should be not found {int}")
    public void body_car_should_be_not_found(int id) {
        response
                .then()
                .body("errors.message", equalTo(ExceptionMessages.NOT_FOUND_MESSAGE.formatted("car", id)));
    }

    @Given("I have update car request")
    public void i_have_update_car_request() {
        carUpdateRequest = updateRequest();
    }


    @When("I send a PUT car request with {int}")
    public void i_send_a_put_car_request_with(int id) {
        response = given()
                .contentType(ContentType.JSON)
                .body(carUpdateRequest)
                .when()
                .put( DEFAULT_CAR_ADDRESS + "/{id}", id);
    }

    @When("I send a DELETE car request with {int}")
    public void i_send_a_delete_car_request_with(int id) {
        response = given()
                .when()
                .delete(DEFAULT_CAR_ADDRESS + "/{id}", id);
    }
}
