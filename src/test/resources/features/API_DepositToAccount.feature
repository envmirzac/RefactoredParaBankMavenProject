Feature: DepositToAccount

  Scenario: DepositToAccount using POST method
    Given the base URI is set from properties file
    And the request parameters are set for account
      | AccountId | amount |
      | 13455     | 100    |
    When a POST request is sent to "/deposit"
    Then status code 200 is received
    And the message "Successfully deposited "$100" to account "#13455"" is displayed

