package by.shestakov.ridesservice.e2e;

import by.shestakov.ridesservice.dto.request.RideRequest;
import by.shestakov.ridesservice.dto.request.RideStatusRequest;
import by.shestakov.ridesservice.dto.request.RideUpdateRequest;
import by.shestakov.ridesservice.dto.response.RideResponse;
import by.shestakov.ridesservice.entity.Status;
import by.shestakov.ridesservice.util.constant.ExceptionMessage;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import static io.restassured.RestAssured.given;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static testConstant.TestConstant.defaultRideDriverNotFoundRequest;
import static testConstant.TestConstant.defaultRidePassengerNotFoundRequest;
import static testConstant.TestConstant.defaultRideRequest;
import static testConstant.TestConstant.defaultRideRequestWithoutCar;
import static testConstant.TestConstant.defaultRideStatusRequest;
import static testConstant.TestConstant.defaultRideUpdateRequest;
import static testConstant.TestConstant.invalidRequest;

public class RidesSteps {

    private RideRequest rideRequest;
    private Response response;
    private RideStatusRequest rideStatusRequest;
    private RideUpdateRequest rideUpdateRequest;

    @When("I send a GET ride request with offset {int} limit {int}")
    public void i_send_a_get_ride_request_with_offset_limit(int offset, int limit) {
        response = given()
                .queryParam("offset", String.valueOf(offset))
                .queryParam("limit", String.valueOf(limit))
                .when()
                .get("/api/v1/rides");
    }

    @Given("I have a ride request")
    public void i_have_a_ride_request() {
        rideRequest = defaultRideRequest();
    }

    @When("I send a POST ride request")
    public void i_send_a_post_ride_request() {
        response = given()
                .contentType(ContentType.JSON)
                .body(rideRequest)
                .when()
                .post("/api/v1/rides");
    }

    @Then("Status should be {int}")
    public void status_should_be(int status) {
        response
                .then()
                .statusCode(status);
    }

    @Given("I have a ride request with not exists driver id")
    public void i_have_a_ride_request_with_not_exists_driver_id() {
        rideRequest = defaultRideDriverNotFoundRequest();
    }

    @Given("I have a ride request with not exists passenger id")
    public void i_have_a_ride_request_with_not_exists_passenger_id() {
        rideRequest = defaultRidePassengerNotFoundRequest();
    }

    @Given("I have a ride request with driver without car")
    public void i_have_ride_request_with_driver_without_car() {
        rideRequest = defaultRideRequestWithoutCar();
    }

    @Given("I have a invalid ride request")
    public void i_have_a_invalid_ride_request() {
        rideRequest = invalidRequest();
    }

    @Given("I send a GET ride request with string {string}")
    public void i_send_a_get_ride_request_with_string(String id) {
        response = given()
                .when()
                .get("/api/v1/rides/{id}", id);
    }

    @And("Body should be not found")
    public void body_should_be_not_found() {
        response.then()
                .body("errors.message", equalTo(ExceptionMessage.NOT_FOUND_MESSAGE));
    }

    @Given("I have a ride status request")
    public void i_have_a_ride_status_request() {
        rideStatusRequest = defaultRideStatusRequest();
    }

    @When("I send a PATCH ride request with string {string}")
    public void i_send_a_patch_ride_request_with_string(String id) {
        response = given()
                .contentType(ContentType.JSON)
                .body(rideStatusRequest)
                .queryParam("rideId", id)
                .when()
                .patch("/api/v1/rides");
    }

    @Given("I have a ride update request")
    public void i_have_a_ride_update_request() {
        rideUpdateRequest = defaultRideUpdateRequest();
    }

    @When("I send a PUT ride request with string {string}")
    public void i_send_a_put_ride_request_with_string(String id) {
        response = given()
                .contentType(ContentType.JSON)
                .body(rideUpdateRequest)
                .queryParam("rideId", id)
                .when()
                .put("/api/v1/rides");
    }

    @And("Body should change status")
    public void body_should_change_status() {
        RideResponse rideResponse = response.as(RideResponse.class);

        assertEquals(Status.ACCEPTED, rideResponse.status());
    }


}
