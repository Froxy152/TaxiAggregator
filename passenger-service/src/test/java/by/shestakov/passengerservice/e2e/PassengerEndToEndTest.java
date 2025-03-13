package by.shestakov.passengerservice.e2e;

import static by.shestakov.passengerservice.constant.TestConstants.DEFAULT_ADDRESS;
import static by.shestakov.passengerservice.constant.TestConstants.alreadyPassengerRequest;
import static by.shestakov.passengerservice.constant.TestConstants.defaultFirstResponse;
import static by.shestakov.passengerservice.constant.TestConstants.defaultRequest;
import static by.shestakov.passengerservice.constant.TestConstants.invalidEmailRequest;
import static by.shestakov.passengerservice.constant.TestConstants.invalidPhoneNumberRequest;
import static by.shestakov.passengerservice.constant.TestConstants.updatePassengerRequest;
import static by.shestakov.passengerservice.constant.TestConstants.updatedPassengerResponse;
import by.shestakov.passengerservice.dto.request.PassengerRequest;
import by.shestakov.passengerservice.dto.request.UpdatePassengerRequest;
import by.shestakov.passengerservice.dto.response.PassengerResponse;
import by.shestakov.passengerservice.util.ExceptionConstants;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import static io.restassured.RestAssured.given;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class PassengerEndToEndTest {

    private PassengerRequest request;
    private UpdatePassengerRequest updatePassengerRequest;
    private Response response;

    @Given("I have a passenger")
    public void i_have_a_passenger() {
        request = defaultRequest();
    }

    @When("I send a POST request")
    public void i_send_a_post_request() {
        response = given()
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .post(DEFAULT_ADDRESS);
    }

    @Then("The response status should be {int}")
    public void the_response_status_should_be(int status) {
        response
                .then()
                .statusCode(status);
    }

    @Given("I have a passenger with exists email")
    public void i_have_a_passenger_with_exists_email() {
        request = alreadyPassengerRequest();
    }

    @Then("Response body should be error response conflict")
    public void response_body_should_be_error_response_conflict() {
        response.
                then()
                .body("errors.message", equalTo(ExceptionConstants.CONFLICT_MESSAGE.formatted(request.email(), request.phoneNumber())));
    }

    @Given("I have a passenger with invalid email")
    public void i_have_a_passenger_with_invalid_email() {
        request = invalidEmailRequest();
    }

    @Given("I have a passenger with invalid phone number")
    public void i_have_a_passenger_with_invalid_phone_number() {
        request = invalidPhoneNumberRequest();
    }

    @When("Get passenger with id {int}")
    public void get_passenger_with_id(int id) {
        response = given()
                .when()
                .get(DEFAULT_ADDRESS + "/{id}", id);
    }

    @Then("Response body should be passenger")
    public void response_body_should_be_passenger() {
        PassengerResponse passengerResponse = response.as(PassengerResponse.class);

        assertEquals(defaultFirstResponse(), passengerResponse);
    }

    @Then("Response body should be error response not found with id {int}")
    public void response_body_should_be_error_response_not_found_with_id(int id) {
        response.
                then()
                .body("errors.message", equalTo(ExceptionConstants.NOT_FOUND_MESSAGE.formatted(id)));

    }

    @Given("I have a update passenger")
    public void i_have_a_update_passenger() {
        updatePassengerRequest = updatePassengerRequest();
    }

    @When("I send a UPDATE request with id {int}")
    public void i_send_a_update_request_with_id(int id) {
        response = given()
                .contentType(ContentType.JSON)
                .body(updatePassengerRequest)
                .when()
                .put(DEFAULT_ADDRESS + "/{id}", id);
    }

    @Then("Response body should be updated passenger")
    public void response_body_should_be_updated_passenger() {
        PassengerResponse passengerResponse = response.as(PassengerResponse.class);

        assertEquals(updatedPassengerResponse(), passengerResponse);
    }

    @When("I send a DELETE request with id {int}")
    public void i_send_a_delete_request_with_id(int id) {
        response = given()
                .when()
                .delete(DEFAULT_ADDRESS + "/{id}", id);
    }

    @When("I send a GET request with offset {int} limit {int}")
    public void i_send_a_get_request_with_offset_limit(int offset, int limit) {
        response = given()
                .queryParam("offset", String.valueOf(offset))
                .queryParam("limit", String.valueOf(limit))
                .when()
                .get(DEFAULT_ADDRESS);

    }
}
