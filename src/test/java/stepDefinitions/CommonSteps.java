package stepDefinitions;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.PropertiesUtil;
import utils.ScenarioContext;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertNotNull;
import io.restassured.path.xml.XmlPath;

import java.util.Map;
import static org.junit.Assert.assertTrue;

public class CommonSteps {

    private static final Logger logger = LoggerFactory.getLogger(CommonSteps.class);
    private final ScenarioContext scenarioContext = ScenarioContext.getInstance();

    protected RequestSpecification getRequest() {
        RequestSpecification request = scenarioContext.getContext("API_REQUEST");
        if (request == null) {
            request = RestAssured.given();
            scenarioContext.setContext("API_REQUEST", request);
        }
        return request;
    }

    @When("^I make a GET request to \"([^\"]*)\"$")
    public void iMakeAGETRequestTo(String endpoint) {
        Response response = getRequest().when().get(endpoint);
        scenarioContext.setContext("API_RESPONSE", response);
        logger.info("Sent GET request to: {}", endpoint);
        logger.debug("Response: {}", response.getBody().asString());
    }

    @When("^I send a POST request to \"([^\"]*)\"$")
    public void iSendAPostRequestTo(String endpoint) {
        Response response = getRequest().when().post(endpoint);
        scenarioContext.setContext("API_RESPONSE", response);
        logger.info("Sent POST request to: {}", endpoint);
        logger.debug("Response: {}", response.getBody().asString());
    }

    @Given("^I set the base URI from properties file$")
    public void iSetTheBaseURIFromPropertiesFile() {
        String baseUri = PropertiesUtil.getProperty("baseURI");
        RestAssured.baseURI = baseUri;
        scenarioContext.setContext("BASE_URI", baseUri);
        logger.info("Base URI set to: {}", baseUri);
    }

    @Then("^I should receive a status code of (\\d+)$")
    public void iShouldReceiveAStatusCodeOf(int statusCode) {
        Response response = scenarioContext.getContext("API_RESPONSE");
        int actualStatusCode = response.getStatusCode();
        assertThat(actualStatusCode).isEqualTo(statusCode);
        logger.info("Expected status code: {}, Actual status code: {}", statusCode, actualStatusCode);
    }

    @Then("^the response should be valid$")
    public void theResponseShouldBeValid() {
        Response response = scenarioContext.getContext("API_RESPONSE");
        assertNotNull(response);
        assertTrue(response.getBody().asString().length() > 0);
        logger.info("Response validated successfully");
    }

    @And("^I set the request parameters with accountId \"([^\"]*)\" and amount \"([^\"]*)\"$")
    public void setRequestParameters(String accountId, String amount) {
        getRequest().queryParam("accountId", accountId);
        getRequest().queryParam("amount", amount);
        logger.info("Request parameters set: accountId={}, amount={}", accountId, amount);
    }

    @And("^I set the request parameters with fromAccountId \"([^\"]*)\", toAccountId \"([^\"]*)\" and amount \"([^\"]*)\"$")
    public void setTransferRequestParameters(String fromAccountId, String toAccountId, String amount) {
        getRequest().queryParam("fromAccountId", fromAccountId);
        getRequest().queryParam("toAccountId", toAccountId);
        getRequest().queryParam("amount", amount);
        logger.info("Transfer parameters set: fromAccountId={}, toAccountId={}, amount={}", fromAccountId, toAccountId, amount);
    }

    @And("^I should see the message \"Successfully deposited \"([^\"]*)\" to account \"([^\"]*)\"\"$")
    public void iShouldSeeTheMessageToAccount(String amount, String accountId) {
        Response response = scenarioContext.getContext("API_RESPONSE");
        String expectedMessage = "Successfully deposited " + amount + " to account " + accountId;
        String responseBody = response.getBody().asString();
        assertThat(responseBody).isEqualTo(expectedMessage);
        logger.info("Validated the response body contains: {}", expectedMessage);
    }

    @And("^I should see the transfer message \"Successfully transferred \"([^\"]*)\" from account \"([^\"]*)\" to account \"([^\"]*)\"\"$")
    public void iShouldSeeTheTransferMessage(String amount, String fromAccountId, String toAccountId) {
        Response response = scenarioContext.getContext("API_RESPONSE");
        String expectedMessage = "Successfully transferred " + amount + " from account " + fromAccountId + " to account " + toAccountId;
        String responseBody = response.getBody().asString();
        assertThat(responseBody).isEqualTo(expectedMessage);
        logger.info("Validated the response body contains: {}", expectedMessage);
    }

    @And("^I should validate the account details:$")
    public void iShouldValidateTheAccountDetails(DataTable dataTable) {
        // Convert the DataTable(from the feature file) into a Map<String, String>.
        Map<String, String> accountDetails = dataTable.asMap(String.class, String.class);

        // Get the API response from scenarioContext and convert it to a string.
        Response response = scenarioContext.getContext("API_RESPONSE");
        String xmlResponse = response.getBody().asString();

        // Iterate through each entry (column) in the accountDetails map.
        for (Map.Entry<String, String> entry : accountDetails.entrySet()) {
            // Extract the field name and expected value for validation.
            String field = entry.getKey();
            String expectedValue = entry.getValue();

            // Construct an XML path expression based on the field name.
            String xmlPathExpression = "account." + field;

            try {
                // Use an XMLPath object to extract the actual value from the XML response.
                String actualValue = new XmlPath(xmlResponse)
                        .setRoot(xmlPathExpression)
                        .getString("");

                // Log the XML path expression and extracted value.
                logger.info("Using XML Path Expression: '{}', Extracted Value: '{}'", xmlPathExpression, actualValue);

                // Assert that the actual value matches the expected value.
                assertThat(actualValue).isEqualTo(expectedValue);

                logger.info("Validated {} - expected: '{}', actual: '{}'", field, expectedValue, actualValue);
            } catch (Exception e) {
                logger.error("Error extracting value from XML: {}", e.getMessage());
            }
        }
    }

}
