Feature:  Ride API

  Scenario: Get all rides
    When I send a GET ride request with offset 0 limit 5
    Then Status should be 200

  Scenario: Create a ride
    Given I have a ride request
    When I send a POST ride request
    Then Status should be 201

  Scenario: Create a ride driver not found
    Given I have a ride request with not exists driver id
    When I send a POST ride request
    Then Status should be 404

  Scenario: Create a ride passenger not found
    Given I have a ride request with not exists passenger id
    When I send a POST ride request
    Then Status should be 404

  Scenario: Create a ride, but driver without car
    Given I have a ride request with driver without car
    When I send a POST ride request
    Then Status should be 400

  Scenario: Create a ride, but invalid request
    Given I have a invalid ride request
    When I send a POST ride request
    Then Status should be 400

  Scenario: Get a ride by id
    When I send a GET ride request with string "67ce92e97960c17aa8048ab5"
    Then Status should be 200

  Scenario: Get a ride by id not found
    When I send a GET ride request with string "not found"
    Then Status should be 404
    And Body should be not found

  Scenario: Update ride status
    Given I have a ride status request
    When I send a PATCH ride request with string "67ce92e97960c17aa8048ab5"
    Then Status should be 200
    And Body should change status

  Scenario: Update ride status, ride not found
    Given I have a ride status request
    When I send a PATCH ride request with string "not_found"
    Then Status should be 404
    And Body should be not found

  Scenario: Update ride
    Given I have a ride update request
    When I send a PUT ride request with string "67ce92e97960c17aa8048ab5"
    Then Status should be 200

  Scenario: Update ride, ride not found
    Given I have a ride update request
    When I send a PUT ride request with string "not_found"
    Then Status should be 404
    And Body should be not found