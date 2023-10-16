Feature: Parabank Open New Account Functionality

  Scenario Outline: Successful Opening of New Account
    Given I am on the Parabank login page
    When I enter hardcoded valid username and password
    And I click on the login button
    And I go to Open New Account page
    When I select the account type as "<accountType>"
    And I select the from account as "<fromAccount>"
    And I click on the open new account button
    Then I should see a new account confirmation message as "<confirmationMessage>"

    Examples:
      | accountType | fromAccount | confirmationMessage |
      | CHECKING    | 13566      | Congratulations, your account is now open.     |
      | SAVINGS     | 13677      | Congratulations, your account is now open.     |
      | CHECKING    | 13788      | Congratulations, your account is now open.     |
      | SAVINGS     | 14232      | Congratulations, your account is now open.     |