Feature: User Database Operations
  @Run
  Scenario: Insert a new user record into the users table
    Given I have a new user record with a random username and password
    When I insert the user record into the database
    Then the record should exist in the users table with that random username
    When I query for a user by their random username
    Then I should receive the correct user details for that random username