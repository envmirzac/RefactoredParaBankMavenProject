@Run
Feature: Parabank Open New Account Functionality

  Background: User is logged in and is on the Open New Account page
    Given the Parabank login page is displayed
    When valid credentials are entered
    And the login button is clicked
    And navigation to the Open New Account page is completed

  Scenario Outline: Successful Opening of New Account
    When the "<accountType>" account type is selected
    And the source account "<fromAccount>" is chosen
    And the open new account button is clicked
    Then a confirmation message "<confirmationMessage>" is displayed for the new account
    When navigation to the account overview is initiated
    Then the Overview page should be displayed
    And the account "<fromAccount>" is visible on the Account Overview page

    Examples:
      | accountType | fromAccount | confirmationMessage |
      | CHECKING    | 13566       | Congratulations, your account is now open. |
      | SAVINGS     | 13677       | Congratulations, your account is now open. |
      | CHECKING    | 13788       | Congratulations, your account is now open. |
      | SAVINGS     | 14232       | Congratulations, your account is now open. |
