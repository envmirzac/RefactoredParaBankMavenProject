Feature: Withdraw money from an account

  Scenario: Withdraw money using POST method
    Given I set the base URI from properties file
    And I set the request parameters with accountId "13455" and amount "5000"
    When I send a POST request to "/withdraw"
    Then I should receive a status code of 200
    And the response should be valid