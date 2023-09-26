Feature: Validate Account Information via API

  Scenario: Verify the details of account 106362
    Given I send a GET request to "/parabank/services/bank/accounts/106362"
    Then the response status should be 200
#    And I should receive the correct account details