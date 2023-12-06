package stepDefinitions;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import pages.OpenNewAccountPage;
import utils.DriverManager;
import utils.ScenarioContext;
import utils.ScenarioContextKeys;

import static org.junit.Assert.assertEquals;

public class UI_OpenNewAccountPageSteps extends BaseDefine {

    private final OpenNewAccountPage openNewAccountPage = new OpenNewAccountPage(DriverManager.getDriver());

    @And("navigation to the Open New Account page is completed")
    public void navigationToTheOpenNewAccountPageIsCompleted() {
        openNewAccountPage.navigateToOpenNewAccountPage();
        logger.info("Navigated to Parabank open new account page.");
    }

    @When("the {string} account type is selected")
    public void theAccountTypeIsSelected(String accountType) {
        openNewAccountPage.selectAccountType(accountType);
        logger.info("Selected account type as: {}", accountType);
    }

    @And("the source account {string} is chosen")
    public void theSourceAccountIsChosen(String accountNumber) {
        openNewAccountPage.selectFromAccount(accountNumber);
        logger.info("Selected from account with number: {}", accountNumber);
    }

    @When("the open new account button is clicked")
    public void theOpenNewAccountButtonIsClicked() {
        openNewAccountPage.submitNewAccountButton();
        logger.info("Clicked on open new account button.");
    }

    @Then("a confirmation message {string} is displayed for the new account")
    public void aConfirmationMessageIsDisplayedForTheNewAccount(String expectedMessage) {
        String actualMessage = openNewAccountPage.getConfirmationMessage();
        logger.info("Verified account confirmation message: {}", expectedMessage);
        assertEquals("The confirmation message should match the expected message",
                expectedMessage, actualMessage);
    }

    @Then("the new account number is displayed on the Open New Account Page")
    public void theNewAccountNumberIsDisplayedOnTheOpenNewAccountPage() {
        String theNewAccount = openNewAccountPage.getNewAccountNumber();
        scenarioContext.saveValueToScenarioContext(ScenarioContextKeys.ScenarioContextKey.NEW_ACCOUNT, theNewAccount);
    }

}
