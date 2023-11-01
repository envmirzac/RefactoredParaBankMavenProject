@Run
Feature: Parabank Open New Account Functionality

  Background: User is logged in and is on the Open New Account page
    # Given
    Given I am on the Parabank login page
    # the user logins on the page
    When I enter valid username and password
    And I click on the login button
    And I go to Open New Account page

  Scenario Outline: Successful Opening of New Account
    When I select the account type as "<accountType>"
    And I select the from account as "<fromAccount>"
    And I click on the open new account button
    Then I should see a new account confirmation message as "<confirmationMessage>"
    # the new acc conf message  ...
    And I click on the account overview button
    And I should be taken to the Overview page
    And "<fromAccount>" account is visible on the Account Overview page
    # To validate the new account

    Examples:
      | accountType | fromAccount | confirmationMessage |
      | CHECKING    | 13566       | Congratulations, your account is now open. |
#      | SAVINGS     | 13677       | Congratulations, your account is now open. |
#      | CHECKING    | 13788       | Congratulations, your account is now open. |
#      | SAVINGS     | 14232       | Congratulations, your account is now open. |
