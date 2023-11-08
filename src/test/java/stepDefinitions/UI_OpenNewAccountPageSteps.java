package stepDefinitions;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pages.OpenNewAccountPage;
import utils.DriverManager;

import static org.junit.Assert.assertEquals;

public class UI_OpenNewAccountPageSteps {

    private static final Logger logger = LoggerFactory.getLogger(UI_OpenNewAccountPageSteps.class);
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
        assertEquals(expectedMessage, actualMessage);
        logger.info("Verified account confirmation message: {}", expectedMessage);
    }

    // Uncomment and implement this method if it's applicable to your tests
    // @And("the account {string} is visible on the Account Overview page")
    // public void theAccountIsVisibleOnTheAccountOverviewPage(String accountNumber) {
    //     boolean isVisible = openNewAccountPage.isAccountVisibleOnOverview(accountNumber);
    //     assertEquals("Account is not visible on the overview page", true, isVisible);
    //     logger.info("Account number: {} is visible on the Account Overview page.", accountNumber);
    // }
}
