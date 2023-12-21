package stepDefinitions;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import utils.PropertiesUtil;
import static org.assertj.core.api.Assertions.assertThat;
import io.restassured.path.xml.XmlPath;
import utils.ScenarioContextKeys;

import java.util.List;
import java.util.Map;

public class ApiSteps extends BaseDefine {

    // using DRY principle -> if a RequestSpecification already exists in the context before creating a new one.
    protected RequestSpecification grabRequest() {
        RequestSpecification request = scenarioContext.getValueFromScenarioContext(ScenarioContextKeys.ScenarioContextKey.API_REQUEST);
        if (request == null) {
            logger.info("Creating a new RequestSpecification instance.");
            request = RestAssured.given();
            scenarioContext.saveValueToScenarioContext(ScenarioContextKeys.ScenarioContextKey.API_REQUEST, request);
        } else {
            logger.info("Reusing existing RequestSpecification instance.");
        }

        return request;
    }


    @Given("^the base URI is set from properties file$")
    public void theBaseUriIsSetFromPropertiesFile() {
        String baseUri = PropertiesUtil.getProperty("baseURI");
        RestAssured.baseURI = baseUri;
        logger.info("Base URI set to: {}", baseUri);
    }

    @And("^the request parameters are set$")
//    public void theRequestParametersAreSet(DataTable dataTable) {
//        // Converts the DataTable into a list of maps. Each map represents a row in the table
//        // Each key-value pair in a map corresponds to a column header and its cell value in that row
//        List<Map<String, String>> data = dataTable.asMaps(String.class, String.class);
//        logger.info("Setting request parameters from DataTable.");
//        for (Map<String, String> params : data) {
//            logger.info("Request parameters for current iteration: {}", params);
//            setRequestParameters(params);
//        }
//    }

    //It passes each element from the stream (each map from the list of maps) to the setRequestParameters method
//    public void theRequestParametersAreSet(DataTable dataTable) {
//        dataTable.asMaps(String.class, String.class)
//                .forEach(this::setRequestParameters);
//    }


    //  The params variable represents each element in the stream (each map from the list of maps),
    //  and it's passed to the setRequestParameters method.

    public void theRequestParametersAreSet(DataTable dataTable) {
        // Converts the provided DataTable into a List of Maps. Each map represents a row in the DataTable.
        // The keys and values in each map are strings, corresponding to the column headers and cell values in the DataTable.
        dataTable.asMaps(String.class, String.class)

                // The forEach method is used to iterate over each map in the list.
                // A lambda expression is provided to define the action to be performed on each element of the stream.
                .forEach(params -> {
                    // Inside the lambda expression, a logging statement is included.
                    // This logs the parameters that are being processed in the current iteration.
                    // The "{}" is a placeholder for the 'params' value, which will be logged.
                    logger.info("Request parameters for current iteration: {}", params);

                    // The setRequestParameters method is called with the current map of parameters.
                    // This method is responsible for applying these parameters to the request.
                    setRequestParameters(params);
                });
    }






    private void setRequestParameters(Map<String, String> params) {
        //takes a map of parameters (params) and adds these parameters to the HTTP request.
        RequestSpecification request = grabRequest();
        //for each entry in the params map, use the "queryParam method to add params to "request""
        params.forEach(request::queryParam);
        logger.info("Request parameters set: {}", params);
    }

    @When("^a POST request is sent to \"([^\"]*)\"$")
    public void aPostRequestIsSentTo(String endpoint) {
        Response response = grabRequest().post(endpoint);
        scenarioContext.saveValueToScenarioContext(ScenarioContextKeys.ScenarioContextKey.API_RESPONSE, response);
        logger.info("Sent POST request to: {}", endpoint);
    }

    // an overloaded method, that has an extra parameter "requestBody". Polymorphism
    public void aPostRequestIsSentTo(String endpoint, String requestBody) {
        Response response = grabRequest().body(requestBody).post(endpoint);
        scenarioContext.saveValueToScenarioContext(ScenarioContextKeys.ScenarioContextKey.API_RESPONSE, response);
        logger.info("Sent POST request with body to: {}", endpoint);
    }

    @Then("^status code (\\d+) is received$")
    public void statusCodeIsReceived(int expectedStatusCode) {
        Response response = scenarioContext.getValueFromScenarioContext(ScenarioContextKeys.ScenarioContextKey.API_RESPONSE);
        int actualStatusCode = response.getStatusCode();
        logger.info("Received status code: {}", actualStatusCode);
        assertThat(actualStatusCode).isEqualTo(expectedStatusCode);
    }

    @And("^the message \"Successfully transferred \"\\$([^\"]*)\" from account \"#([^\"]*)\" to account \"#([^\"]*)\"\" is displayed$")
    public void theTransferMessageIsDisplayed(String amount, String fromAccountId, String toAccountId) {
        Response response = scenarioContext.getValueFromScenarioContext(ScenarioContextKeys.ScenarioContextKey.API_RESPONSE);
        // Adjust the expected message format to match the actual message format
        String expectedMessage = "Successfully transferred $" + amount + " from account #" + fromAccountId + " to account #" + toAccountId;
        String responseBody = response.getBody().asString();
        logger.info("Validated the response body contains: {}", expectedMessage);
        assertThat(responseBody).isEqualTo(expectedMessage);
    }

    @When("^a GET request is sent to \"([^\"]*)\"$")
    public void aGETRequestIsSentTo(String endpoint) {
        Response response = grabRequest().get(endpoint);
        scenarioContext.saveValueToScenarioContext(ScenarioContextKeys.ScenarioContextKey.API_RESPONSE, response);
        logger.info("Sent GET request to: {}", endpoint);
    }

    @And("^the account details are displayed in the response body:$")
    public void theAccountDetailsAreDisplayedInTheResponseBody(DataTable dataTable) {
        // Transform the Cucumber DataTable into a Map
        Map<String, String> expectedDetails = dataTable.asMap(String.class, String.class);
        Response response = scenarioContext.getValueFromScenarioContext(ScenarioContextKeys.ScenarioContextKey.API_RESPONSE);
        String xmlResponse = response.getBody().asString();

        // Iterate through each key-value pair in the map of expected details
        for (Map.Entry<String, String> entry : expectedDetails.entrySet()) {
            String field = entry.getKey();
            String expectedValue = entry.getValue();
            // The 'getString' method will extract the text of the specified node (e.g. <id>13566</id>)
            String actualValue = XmlPath.from(xmlResponse).getString("account." + field);
            logger.info("Validating field: {}, Expected: '{}', Actual: '{}'", field, expectedValue, actualValue);
            assertThat(actualValue).as("Validate field: " + field).isEqualTo(expectedValue);
        }
    }

    @And("^the request parameters are set for account$")
    public void theRequestParametersAreSetForAccount(DataTable dataTable) {
        List<Map<String, String>> data = dataTable.asMaps(String.class, String.class);
        Map<String, String> params = data.get(0);
        String accountId = params.get("AccountId");
        String amount = params.get("amount");
        grabRequest().queryParam("accountId", accountId)
                .queryParam("amount", amount);
        logger.info("New request parameters set: accountId={}, amount={}", accountId, amount);
    }

    @And("^the message \"Successfully deposited \"\\$([^\"]*)\" to account \"#([^\"]*)\"\" is displayed$")
    public void theMessageSuccessfullyDepositedToAccountIsDisplayed(String amount, String accountId) {
        Response response = scenarioContext.getValueFromScenarioContext(ScenarioContextKeys.ScenarioContextKey.API_RESPONSE);
        String expectedMessage = "Successfully deposited $" + amount + " to account #" + accountId;
        String responseBody = response.getBody().asString();
        logger.info("Validated the response body contains: {}", expectedMessage);
        assertThat(responseBody).contains(expectedMessage);
    }

}