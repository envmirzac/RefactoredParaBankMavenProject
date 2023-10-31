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

    @Given("I have a new user record with a random username and password")
    public void i_have_a_new_user_record_with_a_random_username_and_password() {
        String fetchedPassword = PropertiesUtil.getProperty("userPassword");
        String username = generateRandomUsername();
        // Creating a new 'User' object and setting its username and password.
        User user = new User();
        user.setUsername(username);
        user.setPassword(fetchedPassword);
        scenarioContext.setContext("RANDOM_USER", user);
        logger.info("New user record created with username: {} and password: {}", username, fetchedPassword);
    }

    @When("I insert the user record into the database")
    public void i_insert_the_user_record_into_the_database() {
        User user = scenarioContext.getContext("RANDOM_USER");
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        // Saving the 'User' object in the database; Hibernate maps the object to the appropriate database row.
        session.save(user);
        session.getTransaction().commit();
        session.close();
        logger.info("User record with username: {} inserted into the database", user.getUsername());
    }

    @Then("the record should exist in the users table with that random username")
    public void the_record_should_exist_in_the_users_table_with_that_random_username() {
        User user = scenarioContext.getContext("RANDOM_USER");
        // Extracting the username of the 'User' object.
        String actualUsername = user.getUsername();
        Session session = HibernateUtil.getSessionFactory().openSession();
        // Querying the database to fetch a user record based on the username. Hibernate queries simplify database interactions.
        User retrievedUser = (User) session.createQuery("FROM User WHERE username = :username")
                .setParameter("username", actualUsername)
                .uniqueResult();
        session.close();
        // Asserting that the retrieved user is not null, ensuring that the user exists in the database.
        assertNotNull(retrievedUser);
        // Asserting that the username of the retrieved user matches the expected username.
        assertEquals(actualUsername, retrievedUser.getUsername());
        logger.info("Validated that user with username: {} exists in the database", actualUsername);
    }

    @When("I query for a user by their random username")
    public void i_query_for_a_user_by_their_random_username() {
        User expectedUser = scenarioContext.getContext("RANDOM_USER");
        String expectedUsername = expectedUser.getUsername();
        Session session = HibernateUtil.getSessionFactory().openSession();
        // Querying the database to fetch a user record based on the username.
        User retrievedUser = (User) session.createQuery("FROM User WHERE username = :username")
                .setParameter("username", expectedUsername)
                .uniqueResult();
        session.close();
        // Storing the retrieved 'User' object in the ScenarioContext for further steps.
        scenarioContext.setContext("QUERIED_USER", retrievedUser);
        logger.info("Queried for user with username: {}", expectedUsername);
    }

    @Then("I should receive the correct user details for that random username")
    public void i_should_receive_the_correct_user_details_for_that_random_username() {
        User expectedUser = scenarioContext.getContext("RANDOM_USER");
        User queriedUser = scenarioContext.getContext("QUERIED_USER");
        // Asserting that the retrieved user is not null
        assertNotNull(queriedUser);
        // Asserting that the username of the retrieved user matches the expected username
        assertEquals(expectedUser.getUsername(), queriedUser.getUsername());
        logger.info("Received correct user details for username: {}", queriedUser.getUsername());
    }
}
