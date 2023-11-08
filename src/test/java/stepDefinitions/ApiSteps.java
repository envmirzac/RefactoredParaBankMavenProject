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
import io.restassured.path.xml.XmlPath;

import java.util.List;
import java.util.Map;

public class ApiSteps {

    private static final Logger logger = LoggerFactory.getLogger(ApiSteps.class);
    private final ScenarioContext scenarioContext = ScenarioContext.getInstance();

    protected RequestSpecification getRequest() {
        RequestSpecification request = scenarioContext.getContext("API_REQUEST");
        if (request == null) {
            request = RestAssured.given();
            scenarioContext.setContext("API_REQUEST", request);
        }
        return request;
    }

    // New or refactored methods based on the updated feature file
    @Given("^the base URI is set from properties file$")
    public void theBaseUriIsSetFromPropertiesFile() {
        String baseUri = PropertiesUtil.getProperty("baseURI");
        RestAssured.baseURI = baseUri;
        logger.info("Base URI set to: {}", baseUri);
    }


    @And("^the request parameters are set$")
    public void theRequestParametersAreSet(DataTable dataTable) {
        List<Map<String, String>> data = dataTable.asMaps(String.class, String.class);
        // Assuming there is only one row in the data table.
        Map<String, String> params = data.get(0);
        String fromAccountId = params.get("fromAccountId");
        String toAccountId = params.get("toAccountId");
        String amount = params.get("amount");

        getRequest().queryParam("fromAccountId", fromAccountId)
                .queryParam("toAccountId", toAccountId)
                .queryParam("amount", amount);
        logger.info("Request parameters set: fromAccountId={}, toAccountId={}, amount={}", fromAccountId, toAccountId, amount);
    }


    @When("^a POST request is sent to \"([^\"]*)\"$")
    public void aPostRequestIsSentTo(String endpoint) {
        Response response = getRequest().post(endpoint);
        scenarioContext.setContext("API_RESPONSE", response);
        logger.info("Sent POST request to: {}", endpoint);
    }

    @Then("^status code (\\d+) is received$")
    public void statusCodeIsReceived(int expectedStatusCode) {
        Response response = scenarioContext.getContext("API_RESPONSE");
        int actualStatusCode = response.getStatusCode();
        logger.info("Received status code: {}", actualStatusCode);
        assertThat(actualStatusCode).isEqualTo(expectedStatusCode);
    }


    @And("^the message \"Successfully transferred \"\\$([^\"]*)\" from account \"#([^\"]*)\" to account \"#([^\"]*)\"\" is displayed$")
    public void theTransferMessageIsDisplayed(String amount, String fromAccountId, String toAccountId) {
        Response response = scenarioContext.getContext("API_RESPONSE");
        // Adjust the expected message format to match the actual message format
        String expectedMessage = "Successfully transferred $" + amount + " from account #" + fromAccountId + " to account #" + toAccountId;
        String responseBody = response.getBody().asString();
        assertThat(responseBody).contains(expectedMessage);
        logger.info("Validated the response body contains: {}", expectedMessage);
    }


    // Existing methods from the original ApiSteps class
    @When("^a GET request is sent to \"([^\"]*)\"$")
    public void aGETRequestIsSentTo(String endpoint) {
        Response response = getRequest().get(endpoint);
        scenarioContext.setContext("API_RESPONSE", response);
        logger.info("Sent GET request to: {}", endpoint);
    }


    @When("^I send a POST request to \"([^\"]*)\"$")
    public void iSendAPostRequestTo(String endpoint) {
        Response response = getRequest().when().post(endpoint);
        scenarioContext.setContext("API_RESPONSE", response);
        logger.info("Sent POST request to: {}", endpoint);
    }

    @Given("^I set the base URI from properties file$")
    public void iSetTheBaseURIFromPropertiesFile() {
        String baseUri = PropertiesUtil.getProperty("baseURI");
        RestAssured.baseURI = baseUri;
        logger.info("Base URI set to: {}", baseUri);
    }

    @Then("^I should receive a status code of (\\d+)$")
    public void iShouldReceiveAStatusCodeOf(int statusCode) {
        Response response = scenarioContext.getContext("API_RESPONSE");
        int actualStatusCode = response.getStatusCode();
        logger.info("Expected status code: {}, Actual status code: {}", statusCode, actualStatusCode);
        assertThat(actualStatusCode).isEqualTo(statusCode);
    }

    @And("^I set the request parameters with fromAccountId \"([^\"]*)\", toAccountId \"([^\"]*)\" and amount \"([^\"]*)\"$")
    public void setTransferRequestParameters(String fromAccountId, String toAccountId, String amount) {
        getRequest().queryParam("fromAccountId", fromAccountId)
                .queryParam("toAccountId", toAccountId)
                .queryParam("amount", amount);
        logger.info("Transfer parameters set: fromAccountId={}, toAccountId={}, amount={}", fromAccountId, toAccountId, amount);
    }


    @And("^I should see the transfer message \"Successfully transferred \"([^\"]*)\" from account \"([^\"]*)\" to account \"([^\"]*)\"\"$")
    public void iShouldSeeTheTransferMessage(String amount, String fromAccountId, String toAccountId) {
        Response response = scenarioContext.getContext("API_RESPONSE");
        String expectedMessage = "Successfully transferred " + amount + " from account " + fromAccountId + " to account " + toAccountId;
        String responseBody = response.getBody().asString();
        assertThat(responseBody).contains(expectedMessage);
        logger.info("Validated the response body contains: {}", expectedMessage);
    }

    @And("^I should validate the account details:$")
    public void iShouldValidateTheAccountDetails(DataTable dataTable) {
        Map<String, String> accountDetails = dataTable.asMap(String.class, String.class);
        Response response = scenarioContext.getContext("API_RESPONSE");
        String xmlResponse = response.getBody().asString();

        for (Map.Entry<String, String> entry : accountDetails.entrySet()) {
            String field = entry.getKey();
            String expectedValue = entry.getValue();
            String xmlPathExpression = "account." + field;

            try {
                String actualValue = new XmlPath(xmlResponse).setRoot(xmlPathExpression).getString("");
                logger.info("Using XML Path Expression: '{}', Extracted Value: '{}'", xmlPathExpression, actualValue);
                assertThat(actualValue).isEqualTo(expectedValue);
                logger.info("Validated {} - expected: '{}', actual: '{}'", field, expectedValue, actualValue);
            } catch (Exception e) {
                logger.error("Error extracting value from XML: {}", e.getMessage());
            }
        }
    }


    @And("^the account details are displayed in the response body:$")
    public void theAccountDetailsAreDisplayedInTheResponseBody(DataTable dataTable) {
        Map<String, String> expectedDetails = dataTable.asMap(String.class, String.class);
        Response response = scenarioContext.getContext("API_RESPONSE");
        String xmlResponse = response.getBody().asString();

        for (Map.Entry<String, String> entry : expectedDetails.entrySet()) {
            String field = entry.getKey();
            String expectedValue = entry.getValue();
            String actualValue = XmlPath.from(xmlResponse).getString("account." + field); // Adjust this line to correctly specify the XML path
            logger.info("Validating field: {}, Expected: '{}', Actual: '{}'", field, expectedValue, actualValue);
            assertThat(actualValue).as("Validate field: " + field).isEqualTo(expectedValue);
        }
    }

    // Overloaded method to handle the new feature file parameters
    @And("^the request parameters are set for account$")
    public void theRequestParametersAreSetForAccount(DataTable dataTable) {
        List<Map<String, String>> data = dataTable.asMaps(String.class, String.class);
        Map<String, String> params = data.get(0);
        String accountId = params.get("AccountId");
        String amount = params.get("amount");

        getRequest().queryParam("accountId", accountId)
                .queryParam("amount", amount);
        logger.info("New request parameters set: accountId={}, amount={}", accountId, amount);
    }

    @And("^the message \"Successfully deposited \"\\$([^\"]*)\" to account \"#([^\"]*)\"\" is displayed$")
    public void theMessageSuccessfullyDepositedToAccountIsDisplayed(String amount, String accountId) {
        Response response = scenarioContext.getContext("API_RESPONSE");
        String expectedMessage = "Successfully deposited $" + amount + " to account #" + accountId;
        String responseBody = response.getBody().asString();
        logger.info("Validated the response body contains: {}", expectedMessage);
        assertThat(responseBody).contains(expectedMessage);
    }


}
