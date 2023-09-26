Feature: Parabank Open New Account Functionality

  Scenario: Successful Opening of New Account
    Given I am on the Parabank login page
    When I enter valid username and password
    And I click on the login button
    And I am on the Parabank open new account page
    When I select the account type
    And I select the from account
    And I click on the open new account button
    Then I should see a new account confirmation message
