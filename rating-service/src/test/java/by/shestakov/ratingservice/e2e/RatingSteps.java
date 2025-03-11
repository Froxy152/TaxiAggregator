package by.shestakov.ratingservice.e2e;

import static by.shestakov.ratingservice.constant.TestConstant.TEST_COMMENTARY_DTO;
import static by.shestakov.ratingservice.constant.TestConstant.defaultRatingByPassengerForInsert;
import static by.shestakov.ratingservice.constant.TestConstant.defaultRatingDriverRequest;
import static by.shestakov.ratingservice.constant.TestConstant.defaultRatingDriverRequestInvalidDriverID;
import static by.shestakov.ratingservice.constant.TestConstant.defaultRatingDriverRequestInvalidPassengerID;
import static by.shestakov.ratingservice.constant.TestConstant.defaultRatingDriverRequestInvalidRideID;
import static by.shestakov.ratingservice.constant.TestConstant.defaultRatingDriverResponse;
import by.shestakov.ratingservice.dto.request.CommentaryDto;
import by.shestakov.ratingservice.dto.request.RatingRequest;
import by.shestakov.ratingservice.dto.response.RatingResponse;
import io.cucumber.java.BeforeAll;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import static io.restassured.RestAssured.given;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;


public class RatingSteps {

    private Response response;
    private RatingRequest ratingRequest;
    private CommentaryDto commentary;



    @Given("I have a rating request")
    public void i_have_a_rating_request() {
        ratingRequest = defaultRatingDriverRequest();
    }

    @When("I send a POST request")
    public void i_send_a_post_request() {
        response = given()
                .contentType(ContentType.JSON)
                .body(ratingRequest)
                .when()
                .post("/api/v1/ratings");
    }




    @Then("Status should be is {int}")
    public void status_should_be_is(int status) {
        response
                .then()
                .statusCode(status);
    }

    @Given("I have a rating request with invalid driverId")
    public void i_have_a_rating_request_with_invalid_driverId() {
        ratingRequest = defaultRatingDriverRequestInvalidDriverID();
    }

    @Given("I have a rating request with invalid passengerId")
    public void i_have_a_rating_request_with_invalid_passengerId() {
        ratingRequest = defaultRatingDriverRequestInvalidPassengerID();
    }

    @Given("I have a rating request with invalid rideId")
    public void i_have_a_rating_request_with_invalid_rideId() {
        ratingRequest = defaultRatingDriverRequestInvalidRideID();
    }

    @When("I send a GET request with offset {int} limit {int}")
    public void i_send_a_get_request_with_offset_limitV(int offset, int limit) {
        response = given()
                .queryParam("offset", String.valueOf(offset))
                .queryParam("limit", String.valueOf(limit))
                .when()
                .get("/api/v1/ratings");
    }

    @Given("I have a new commentary")
    public void i_have_a_new_commentary() {
        commentary = TEST_COMMENTARY_DTO;
    }

    @When("I send a PATCH request with id {string}")
    public void i_send_a_patch_request_with_id(String id) {

        response = given()
                .contentType(ContentType.JSON)
                .body(commentary)
                .when()
                .patch("/api/v1/ratings/{id}", id);

    }







}
