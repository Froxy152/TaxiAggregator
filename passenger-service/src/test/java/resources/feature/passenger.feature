Feature: Passenger API

  Scenario: Create a new passenger
    Given I have a passenger
    When I send a POST request
    Then The response status should be 201

  Scenario: Create a new passenger with exists email and phone number
    Given I have a passenger with exists email
    When I send a POST request
    Then The response status should be 409
    And Response body should be error response conflict

  Scenario: Create a new Passenger with invalid email
    Given I have a passenger with invalid email
    When I send a POST request
    Then The response status should be 400

  Scenario: Create a new Passenger with invalid phone number
    Given I have a passenger with invalid phone number
    When I send a POST request
    Then The response status should be 400

  Scenario: Get a passenger by id
    When Get passenger with id 1
    Then The response status should be 200
    And Response body should be passenger

  Scenario: Get a passenger by id not found
    When Get passenger with id 999
    Then The response status should be 404
    And Response body should be error response not found with id 999

  Scenario: Update a passenger by id
    Given I have a update passenger
    When I send a UPDATE request with id 3
    Then The response status should be 200
    And Response body should be updated passenger

  Scenario: Update a passenger by id not found
    Given I have a update passenger
    When I send a UPDATE request with id 999
    Then The response status should be 404
    And Response body should be error response not found with id 999

  Scenario: Update a passenger by id phone number already exists
    Given I have a update passenger
    When I send a UPDATE request with id 3
    Then The response status should be 409

  Scenario: Update a passenger by id email already exists
    Given I have a update passenger
    When I send a UPDATE request with id 3
    Then The response status should be 409

  Scenario: Soft delete a passenger by id
    When I send a DELETE request with id 4
    Then The response status should be 204

  Scenario: Soft delete a passenger by id not found
    When I send a DELETE request with id 999
    Then The response status should be 404
    And Response body should be error response not found with id 999

  Scenario: Get all passengers
    When I send a GET request with offset 0 limit 5
    Then The response status should be 200
