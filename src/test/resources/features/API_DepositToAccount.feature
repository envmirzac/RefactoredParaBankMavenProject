Feature: DepositToAccount

  @Run
  Scenario: DepositToAccount using POST method
    Given I set the base URI from properties file
    And I set the request parameters with accountId "13455" and amount "5000"
    When I send a POST request to "/deposit"
    Then I should receive a status code of 200
    And I should see the message "Successfully deposited "$5000" to account "#13455""