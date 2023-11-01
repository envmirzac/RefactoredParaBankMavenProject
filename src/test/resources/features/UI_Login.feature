Feature: Parabank Login Functionality

  Scenario: Successful login to Parabank
    Given I am on the Parabank login page
    When I enter valid username and password
    And I click on the login button
    Then I should be taken to the Overview page
