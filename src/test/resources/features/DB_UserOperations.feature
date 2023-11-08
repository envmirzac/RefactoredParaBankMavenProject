Feature: User Database Operations

  Scenario: Insert a new user record into the users table
    Given a new user with a random username is created
    When the user is persisted in the database
    Then the record should exist in the users table with that random username
    When the user is queried by its username
    Then the correct user details should be retrieved from the users table
