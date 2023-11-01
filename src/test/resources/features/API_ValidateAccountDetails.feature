Feature: Validate Account Details

  Scenario: Validate Account Details for a Specific Account
    Given I set the base URI from properties file
    When I make a GET request to "/accounts/13566"
    Then I should receive a status code of 200
    And I should validate the account details:
      | id         | 13566      |
      | customerId | 12434      |
      | type       | CHECKING   |
