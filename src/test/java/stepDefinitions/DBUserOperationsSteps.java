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

// This class handles the steps related to database operations for users.
public class DBUserOperationsSteps {

    // Logger instance to log information and errors.
    private static final Logger logger = LoggerFactory.getLogger(DBUserOperationsSteps.class);

    // Instance of ScenarioContext to share data between steps.
    private final ScenarioContext scenarioContext = ScenarioContext.getInstance();

    private String generateRandomUsername() {
        // Generate a random number between 100 and 999.
        int randomNumber = new Random().nextInt(900) + 100;
        return "VictorMirzac" + randomNumber;
    }
    @Given("I have a new user record with a random username and password")
    public void i_have_a_new_user_record_with_a_random_username_and_password() {
        String fetchedPassword = PropertiesUtil.getProperty("userPassword");
        String username = generateRandomUsername();
        User user = new User();
        user.setUsername(username);
        user.setPassword(fetchedPassword);
        scenarioContext.setContext("RANDOM_USER", user); // Store the user object in the ScenarioContext.
        logger.info("New user record created with username: {} and password: {}", username, fetchedPassword);
    }


    //Inserting the user record into the database.
    @When("I insert the user record into the database")
    public void i_insert_the_user_record_into_the_database() {
        // Retrieve the user object from ScenarioContext.
        User user = scenarioContext.getContext("RANDOM_USER");
        Session session = HibernateUtil.getSessionFactory().openSession(); // Create a Hibernate session.
        session.beginTransaction(); // Begin a Hibernate transaction.
        session.save(user); // Save the user object into the database.
        session.getTransaction().commit(); // Commit the transaction.
        session.close(); // Close the Hibernate session.
        logger.info("User record with username: {} inserted into the database", user.getUsername()); // Log the action.
    }

    // Verify the existence of the newly created user record in the database.
    @Then("the record should exist in the users table with that random username")
    public void the_record_should_exist_in_the_users_table_with_that_random_username() {
        // Retrieve the user object from ScenarioContext.
        User user = scenarioContext.getContext("RANDOM_USER");
        String actualUsername = user.getUsername(); // Get the username from the user object.

        Session session = HibernateUtil.getSessionFactory().openSession(); // Get a Hibernate session.
        // Query the database for the user with the given username.
        User retrievedUser = (User) session.createQuery("FROM User WHERE username = :username")
                .setParameter("username", actualUsername)
                .uniqueResult();
        session.close(); // Close the Hibernate session.

        // Assertions to ensure that the retrieved user exists and has the expected username.
        assertNotNull(retrievedUser);
        assertEquals(actualUsername, retrievedUser.getUsername());

        logger.info("Validated that user with username: {} exists in the database", actualUsername);
    }

    // Cucumber step definition to query the database for a user by their random username.
    @When("I query for a user by their random username")
    public void i_query_for_a_user_by_their_random_username() {
        // Retrieve the expected user object from ScenarioContext.
        User expectedUser = scenarioContext.getContext("RANDOM_USER");
        String expectedUsername = expectedUser.getUsername(); // Get the username from the expected user object.

        Session session = HibernateUtil.getSessionFactory().openSession(); // Get a Hibernate session.
        // Query the database for the user with the given username.
        User retrievedUser = (User) session.createQuery("FROM User WHERE username = :username")
                .setParameter("username", expectedUsername)
                .uniqueResult();
        session.close(); // Close the Hibernate session.
        scenarioContext.setContext("QUERIED_USER", retrievedUser); // Store the retrieved user object in ScenarioContext.

        logger.info("Queried for user with username: {}", expectedUsername); // Log the query action.
    }

    @Then("I should receive the correct user details for that random username")
    public void i_should_receive_the_correct_user_details_for_that_random_username() {
        // Retrieve the expected and queried user objects from ScenarioContext.
        User expectedUser = scenarioContext.getContext("RANDOM_USER");
        User queriedUser = scenarioContext.getContext("QUERIED_USER");

        assertNotNull(queriedUser);
        assertEquals(expectedUser.getUsername(), queriedUser.getUsername());

        logger.info("Received correct user details for username: {}", queriedUser.getUsername()); // Log the validation.
    }
}
