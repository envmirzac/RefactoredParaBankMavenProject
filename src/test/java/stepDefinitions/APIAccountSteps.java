package stepDefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.restassured.RestAssured;
import io.restassured.response.Response;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class APIAccountSteps {
    private Response response;

    @Given("I send a GET request to {string}")
    public void i_send_a_get_request_to(String endpoint) {
        response = RestAssured.get("http://para.testar.org" + endpoint);
    }

    @Then("the response status should be {int}")
    public void the_response_status_should_be(int statusCode) {
        assertEquals(statusCode, response.getStatusCode());
    }

    @Then("I should receive the correct account details")
    public void i_should_receive_the_correct_account_details() {
        // Here you can validate specific details of the account.
        // As an example, if you expect the account ID to be 106362:
        System.out.println(response.asString());
        assertTrue(response.asString().contains("\"id\":106362"));
        // Add more assertions as needed based on the expected account details.
    }
}
