Feature: Transfer funds between two accounts

  Scenario: Transfer a valid amount between two accounts
    Given I set the base URI from properties file
    And I set the request parameters with fromAccountId "13455", toAccountId "13566" and amount "100"
    When I send a POST request to "/transfer"
    Then I should receive a status code of 200
    And the response should be valid
    And I should see the transfer message "Successfully transferred "$100" from account "#13455" to account "#13566""