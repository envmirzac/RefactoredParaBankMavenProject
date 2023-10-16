package stepDefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.ScenarioContext;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class APIAccountSteps {
    private static final Logger logger = LoggerFactory.getLogger(APIAccountSteps.class);
    private final ScenarioContext scenarioContext = ScenarioContext.getInstance();


    @Given("I send a GET request to {string}")
    public void i_send_a_get_request_to(String endpoint) {
        Response response = RestAssured.get("http://localhost:8080" + endpoint);
        logger.info("Sent GET request to: {}", endpoint);

        // Store the response in ScenarioContext
        scenarioContext.setContext("API_RESPONSE", response);
    }

    @Then("the response status should be {int}")
    public void the_response_status_should_be(int statusCode) {
        // Retrieve the response from ScenarioContext
        Response response = scenarioContext.getContext("API_RESPONSE");

        int actualStatusCode = response.getStatusCode();
        assertEquals(statusCode, actualStatusCode);
        logger.info("Expected status code: {}, Actual status code: {}", statusCode, actualStatusCode);
    }

    @Then("I should receive the correct account details")
    public void i_should_receive_the_correct_account_details() {
        // Retrieve the response from ScenarioContext
        Response response = scenarioContext.getContext("API_RESPONSE");

        String responseBody = response.asString();
        logger.debug("Received response body: {}", responseBody);
        assertTrue(responseBody.contains("\"id\":106362"));
        logger.info("Validated that response contains expected account ID: 106362");
        // Add more assertions and log statements as needed based on the expected account details.
    }
}
