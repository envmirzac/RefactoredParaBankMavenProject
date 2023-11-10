
Feature: Transfer funds between two accounts
  Scenario: Transfer a valid amount between two accounts
    Given the base URI is set from properties file
    And the request parameters are set
      | fromAccountId | toAccountId | amount |
      | 13455         | 13566       | 100    |
    When a POST request is sent to "/transfer"
    Then status code 200 is received
    And the message "Successfully transferred "$100" from account "#13455" to account "#13566"" is displayed
