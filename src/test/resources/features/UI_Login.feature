Feature: Parabank Login Functionality
@Run
  Scenario: Successful login to Parabank
    Given the Parabank login page is displayed
    When valid credentials are entered
    And the login button is clicked
    Then the Overview page should be displayed
