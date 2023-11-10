Feature: Validate Account Details
  @Run
  Scenario: Validate Account Details for a Specific Account
    Given the base URI is set from properties file
    When a GET request is sent to "/accounts/13566"
    Then status code 200 is received
    And the account details are displayed in the response body:
      | id         | 13566      |
      | customerId | 12434      |
      | type       | CHECKING   |
