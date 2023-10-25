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
import io.restassured.path.xml.XmlPath; // Used for handling XML responses in RestAssured.
import java.util.Map;

public class ApiSteps {

    private static final Logger logger = LoggerFactory.getLogger(ApiSteps.class);

    // It's final because we won't change this reference.
    private final ScenarioContext scenarioContext = ScenarioContext.getInstance();

    // This protected method returns a RequestSpecification object, either from the context or a new one if not present.
    protected RequestSpecification getRequest() {
        RequestSpecification request = scenarioContext.getContext("API_REQUEST");
        if (request == null) {
            request = RestAssured.given(); // If null, initialize it with default settings.
            scenarioContext.setContext("API_REQUEST", request); // Save the new request specification in context.
        }
        return request; // Return the request specification (new or existing).
    }

    // This method, annotated with 'When', defines a test step for sending a GET request to a specified endpoint.
    @When("^I make a GET request to \"([^\"]*)\"$")
    public void iMakeAGETRequestTo(String endpoint) {
        Response response = getRequest().when().get(endpoint); // Sending a GET request to the specified endpoint (/accounts/13566) and storing the response.
        scenarioContext.setContext("API_RESPONSE", response);
        logger.info("Sent GET request to: {}", endpoint);
        logger.debug("Response: {}", response.getBody().asString());
    }

    @When("^I send a POST request to \"([^\"]*)\"$")
    public void iSendAPostRequestTo(String endpoint) {
        Response response = getRequest().when().post(endpoint); // Sending a POST request to the specified endpoint and storing the response.
        scenarioContext.setContext("API_RESPONSE", response);
        logger.info("Sent POST request to: {}", endpoint);
        logger.debug("Response: {}", response.getBody().asString());
    }

    @Given("^I set the base URI from properties file$")
    public void iSetTheBaseURIFromPropertiesFile() {
        String baseUri = PropertiesUtil.getProperty("baseURI");
        RestAssured.baseURI = baseUri; // Setting the base URI for RestAssured.
//        scenarioContext.setContext("BASE_URI", baseUri);
        logger.info("Base URI set to: {}", baseUri);
    }

    @Then("^I should receive a status code of (\\d+)$")
    public void iShouldReceiveAStatusCodeOf(int statusCode) {
        Response response = scenarioContext.getContext("API_RESPONSE");
        int actualStatusCode = response.getStatusCode();
        assertThat(actualStatusCode).isEqualTo(statusCode);
        logger.info("Expected status code: {}, Actual status code: {}", statusCode, actualStatusCode);
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
        String expectedMessage = "Successfully deposited " + amount + " to account " + accountId; // Forming the expected message string (see the response body in Postman)
        String responseBody = response.getBody().asString();
        assertThat(responseBody).isEqualTo(expectedMessage);
        logger.info("Validated the response body contains: {}", expectedMessage);
    }

    @And("^I should see the transfer message \"Successfully transferred \"([^\"]*)\" from account \"([^\"]*)\" to account \"([^\"]*)\"\"$")
    public void iShouldSeeTheTransferMessage(String amount, String fromAccountId, String toAccountId) {
        Response response = scenarioContext.getContext("API_RESPONSE");
        String expectedMessage = "Successfully transferred " + amount + " from account " + fromAccountId + " to account " + toAccountId; // Forming the expected message string.
        String responseBody = response.getBody().asString(); // Getting the response body as a string.
        assertThat(responseBody).isEqualTo(expectedMessage);
        logger.info("Validated the response body contains: {}", expectedMessage);
    }

    @And("^I should validate the account details:$")
    public void iShouldValidateTheAccountDetails(DataTable dataTable) {
        Map<String, String> accountDetails = dataTable.asMap(String.class, String.class); // Converting the data table into a map for easier processing.
        Response response = scenarioContext.getContext("API_RESPONSE"); // Retrieving the stored response from the scenario context.
        String xmlResponse = response.getBody().asString(); // Getting the response body as a string.

        // Iterating over each entry in the account details map.
        for (Map.Entry<String, String> entry : accountDetails.entrySet()) {
            String field = entry.getKey(); // The field name ("id", customerId" and "type" ).
            String expectedValue = entry.getValue(); // The expected value of that field ("13566", "12434" and CHECKING)
            // Creating an XML path expression to retrieve the value (account.id, account.customerId and account.type)
            String xmlPathExpression = "account." + field;

            // Try-catch block to handle potential exceptions during XML parsing.
            try {
                String actualValue = new XmlPath(xmlResponse).setRoot(xmlPathExpression).getString(""); // Using XML path to extract the actual value from the response.
                logger.info("Using XML Path Expression: '{}', Extracted Value: '{}'", xmlPathExpression, actualValue); // Logging the XML path used and the value extracted.
                assertThat(actualValue).isEqualTo(expectedValue); // Asserting that the actual value matches the expected value.
                logger.info("Validated {} - expected: '{}', actual: '{}'", field, expectedValue, actualValue); // Logging the validation of each field.
            } catch (Exception e) {
                logger.error("Error extracting value from XML: {}", e.getMessage()); // Logging an error message if there's an exception.
            }
        }
    }
}
