Feature: Rating API

  Scenario: Create Review
    Given I have a rating request
    When I send a POST request
    Then Status should be is 201

  Scenario: Create review user can send 1 review
    Given I have a rating request
    When I send a POST request
    Then Status should be is 409

  Scenario: Create review not found driver
    Given I have a rating request with invalid driverId
    When I send a POST request
    Then Status should be is 404

  Scenario: Create review not found passenger
    Given I have a rating request with invalid passengerId
    When I send a POST request
    Then Status should be is 404

  Scenario: Create review not found ride
    Given I have a rating request with invalid rideId
    When I send a POST request
    Then Status should be is 404

  Scenario: Get all reviews
    When I send a GET request with offset 0 limit 1
    Then  Status should be is 200

  Scenario: Update commentary under review
    Given I have a new commentary
    When  I send a PATCH request with id "67d0a45fb5f64f406e63e66c"
    Then Status should be is 200

  Scenario: Update commentary under review not found ride
    Given I have a new commentary
    When I send a PATCH request with id "invalid_id"
    Then Status should be is 404

