package stepDefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import model.User;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.HibernateUtil;
import utils.PropertiesUtil;
import utils.ScenarioContext;

import java.util.Random;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class DBUserOperationsSteps {

    private static final Logger logger = LoggerFactory.getLogger(DBUserOperationsSteps.class);
    private final ScenarioContext scenarioContext = ScenarioContext.getInstance();

    private String generateRandomUsername() {
        int randomNumber = new Random().nextInt(9000) + 100; // Between 100 and 9999
        return "VictorMirzac" + randomNumber;
    }

    @Given("a new user with a random username is created")
    public void aNewUserWithARandomUsernameIsCreated() {
        String fetchedPassword = PropertiesUtil.getProperty("userPassword");
        String username = generateRandomUsername();
        User user = new User();
        user.setUsername(username);
        user.setPassword(fetchedPassword);
        scenarioContext.setContext("RANDOM_USER", user);
        logger.info("New user record created with username: {} and password: {}", username, fetchedPassword);
    }

    @When("the user is persisted in the database")
    public void theUserIsPersistedInTheDatabase() {
        User user = scenarioContext.getContext("RANDOM_USER");
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.save(user);
        session.getTransaction().commit();
        session.close();
        logger.info("User record with username: {} inserted into the database", user.getUsername());
    }

    @Then("the record should exist in the users table with that random username")
    public void theRecordShouldExistInTheUsersTableWithThatRandomUsername() {
        User user = scenarioContext.getContext("RANDOM_USER");
        String actualUsername = user.getUsername();
        Session session = HibernateUtil.getSessionFactory().openSession();
        User retrievedUser = (User) session.createQuery("FROM User WHERE username = :username")
                .setParameter("username", actualUsername)
                .uniqueResult();
        session.close();
        assertNotNull(retrievedUser);
        assertEquals(actualUsername, retrievedUser.getUsername());
        logger.info("Validated that user with username: {} exists in the database", actualUsername);
    }

    @When("the user is queried by its username")
    public void theUserIsQueriedByItsUsername() {
        User expectedUser = scenarioContext.getContext("RANDOM_USER");
        String expectedUsername = expectedUser.getUsername();
        Session session = HibernateUtil.getSessionFactory().openSession();
        User retrievedUser = (User) session.createQuery("FROM User WHERE username = :username")
                .setParameter("username", expectedUsername)
                .uniqueResult();
        session.close();
        scenarioContext.setContext("QUERIED_USER", retrievedUser);
        logger.info("Queried for user with username: {}", expectedUsername);
    }

    @Then("the correct user details should be retrieved from the users table")
    public void theCorrectUserDetailsShouldBeRetrievedFromTheUsersTable() {
        User expectedUser = scenarioContext.getContext("RANDOM_USER");
        User queriedUser = scenarioContext.getContext("QUERIED_USER");
        assertNotNull(queriedUser);
        assertEquals(expectedUser.getUsername(), queriedUser.getUsername());
        logger.info("Received correct user details for username: {}", queriedUser.getUsername());
    }
}
