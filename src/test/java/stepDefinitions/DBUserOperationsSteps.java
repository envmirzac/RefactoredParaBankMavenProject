package stepDefinitions;

import dao.UserDAO;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import model.User;
import utils.PropertiesUtil;

import java.util.Random;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class DBUserOperationsSteps extends BaseSteps {

    private final UserDAO userDao = new UserDAO();

    private String generateRandomUsername() {
        int randomNumber = new Random().nextInt(9000) + 100;
        return "VictorMirzac" + randomNumber;
    }

    @Given("a new user with a random username is created")
    public void aNewUserWithARandomUsernameIsCreated() {
        String fetchedPassword = PropertiesUtil.getProperty("userPassword");
        String username = generateRandomUsername();
        User user = new User(username, fetchedPassword);
        scenarioContext.saveValueToScenarioContext("RANDOM_USER", user);
        logger.info("New user record created with username: {} and password: {}", username, fetchedPassword);
    }

    @When("the user is persisted in the database")
    public void theUserIsPersistedInTheDatabase() {
        User user = scenarioContext.getValueFromScenarioContext("RANDOM_USER");
        userDao.saveUser(user);
        logger.info("User record with username: {} inserted into the database", user.getUsername());
    }

    @Then("the record should exist in the users table with that random username")
    public void theRecordShouldExistInTheUsersTableWithThatRandomUsername() {
        User user = scenarioContext.getValueFromScenarioContext("RANDOM_USER");
        String actualUsername = user.getUsername();
        User retrievedUser = userDao.getUserByUsername(actualUsername);
        logger.info("Validated that user with username: {} exists in the database", actualUsername);
        assertNotNull(retrievedUser);
        assertEquals(actualUsername, retrievedUser.getUsername());
    }

    @When("the user is queried by its username")
    public void theUserIsQueriedByItsUsername() {
        User expectedUser = scenarioContext.getValueFromScenarioContext("RANDOM_USER");
        String expectedUsername = expectedUser.getUsername();
        User retrievedUser = userDao.getUserByUsername(expectedUsername);
        scenarioContext.saveValueToScenarioContext("QUERIED_USER", retrievedUser);
        logger.info("Queried for user with username: {}", expectedUsername);
    }

    @Then("the correct user details should be retrieved from the users table")
    public void theCorrectUserDetailsShouldBeRetrievedFromTheUsersTable() {
        User expectedUser = scenarioContext.getValueFromScenarioContext("RANDOM_USER");
        User queriedUser = scenarioContext.getValueFromScenarioContext("QUERIED_USER");
        logger.info("Received correct user details for username: {}", queriedUser.getUsername());
        assertNotNull(queriedUser);
        assertEquals(expectedUser.getUsername(), queriedUser.getUsername());
    }
}
