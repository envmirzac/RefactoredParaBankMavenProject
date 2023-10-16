package stepDefinitions;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pages.OpenNewAccountPage;
import utils.DriverManager;

import static org.junit.Assert.assertEquals;

public class OpenNewAccountPageSteps {

    private static final Logger logger = LoggerFactory.getLogger(OpenNewAccountPageSteps.class);
    private final OpenNewAccountPage openNewAccountPage;

    public OpenNewAccountPageSteps() {
        openNewAccountPage = new OpenNewAccountPage(DriverManager.getDriver());
    }

    @When("I am on the Parabank open new account page")
    public void i_am_on_the_Parabank_open_new_account_page() {
        openNewAccountPage.navigateToOpenNewAccountPage();
        logger.info("Navigated to Parabank open new account page.");
    }

    @When("I select the account type as {string}")
    public void i_select_the_account_type_as(String accountType) {
        openNewAccountPage.selectAccountType(accountType);
        logger.info("Selected account type as: {}", accountType);
    }

    @When("I select the from account as {string}")
    public void i_select_the_from_account_as(String accountNumber) {
        openNewAccountPage.selectFromAccount(accountNumber);
        logger.info("Selected from account with number: {}", accountNumber);
    }

    @When("I click on the open new account button")
    public void i_click_on_the_open_new_account_button() {
        openNewAccountPage.submitNewAccountButton();
        logger.info("Clicked on open new account button.");
    }

    @Then("I should see a new account confirmation message as {string}")
    public void i_should_see_a_new_account_confirmation_message_as(String expectedMessage) {
        String actualMessage = openNewAccountPage.getConfirmationMessage();
        assertEquals(expectedMessage, actualMessage);
        logger.info("Verified account confirmation message: {}", expectedMessage);
    }

    @And("I go to Open New Account page")
    public void iGoToOpenNewAccountPage() {
        openNewAccountPage.navigateToOpenNewAccountPage();
        logger.info("Navigated to Open New Account page.");
    }

    @And("I click on logout button")
    public void iClickOnLogoutButton() {
        openNewAccountPage.clickLogoutButton();
        logger.info("Clicked on logout button.");
    }
}
