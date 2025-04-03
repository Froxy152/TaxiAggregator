Feature: Driver API

  Scenario: Get all drivers
    When I send a GET driver request offset 0 limit 2
    Then The response should be 200

  Scenario: Get Driver by id
    When I send a GET request 1
    Then The response should be 200
    And valid get response check
  
  Scenario: Get Driver by id not found
    When I send a GET request 999
    Then The response should be 404
    And Body driver should be not found 999
    
  Scenario: Create new Driver
    Given I have a Driver
    When  I send a POST driver request
    Then The response should be 201
    And valid response check

  Scenario: Create new Driver phone number already exists
    Given I have a Driver with exists number
    When I send a POST driver request
    Then The response should be 409
    And Body driver should be conflict

  Scenario: Create new Driver email already exists
    Given I have a Driver with exists email
    When  I send a POST driver request
    Then The response should be 409
    And Body driver should be conflict

  Scenario: Update Driver
    Given I have updated driver request
    When I send a PUT driver request with 3
    Then The response should be 200

  Scenario: Update Driver id not found
    Given I have updated driver request
    When I send a PUT driver request with 999
    Then The response should be 404
    And Body driver should be not found 999

  Scenario: Update Driver email, phone number already exists
    Given I have already exists updated driver request
    When  I send a PUT driver request with 3
    Then  The response should be 409
    And Body driver should be conflict

  Scenario: Soft delete driver
    When I send a DELETE driver request with 3
    Then The response should be deleted 204

  Scenario: Soft delete driver id not found
    When I send a DELETE driver request with 999
    Then The response should be 404
    And Body driver should be not found 999

  Scenario: Get all cars
    When I send a GET request with offset 0 limit 4
    Then The response should be 200

  Scenario: Create a new Car driver not found
    Given I have a car
    When I send a POST car request with 999
    Then The response should be 404
    And Body driver should be not found 999

  Scenario: Create a new Car
    Given I have a car
    When I send a POST car request with 1
    Then The response should be 201
    And validate car response

  Scenario: Create a new Car car number exists
    Given I have a car
    When I send a POST car request with 1
    Then The response should be 409
    And Body car should be conflict

  Scenario: Update a car not found
    Given I have update car request
    When I send a PUT car request with 999
    Then The response should be 404
    And Body car should be not found 999

  Scenario: Update a car
    Given I have update car request
    When I send a PUT car request with 1
    Then The response should be 200

  Scenario: Update a car, car number exists
    Given I have update car request
    When I send a PUT car request with 1
    Then The response should be 409
    And Body car should be conflict

  Scenario: Soft delete car
    When I send a DELETE car request with 3
    Then The response should be deleted 204

  Scenario: Soft delete car not found
    When I send a DELETE car request with 999
    Then The response should be 404
    And Body car should be not found 999