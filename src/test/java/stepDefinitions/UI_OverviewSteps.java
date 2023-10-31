package stepDefinitions;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.junit.Assert;
import pages.OverviewPage;
import utils.DriverManager;

public class UI_OverviewSteps {

    private static final Logger logger = LoggerFactory.getLogger(UI_OverviewSteps.class); // Corrected the logger class reference
    private final OverviewPage overviewPage = new OverviewPage(DriverManager.getDriver());

    @And("{string} account is visible on the Account Overview page")
    public void accountIsVisibleOnTheAccountOverviewPage(String accountNumber) {
        boolean isVisible = overviewPage.isAccountVisible(accountNumber);

        // Logging for better traceability
        if (isVisible) {
            logger.info("Account number {} is visible on the Account Overview page", accountNumber);
        } else {
            logger.error("Account number {} is NOT visible on the Account Overview page", accountNumber);
        }

        // Asserting that the account number should be visible. If it's not, this assertion will fail the test with the provided message.
        Assert.assertTrue("The expected account number is not visible on the Overview page.", isVisible);
    }

    @And("I click on the account overview button")
    public void iClickOnTheAccountOverviewButton() {
        overviewPage.submitAccountOverviewButton();
    }

    @Then("I should be taken to the Overview page")
    public void i_should_be_taken_to_the_overview_page() {
        boolean isAtOverview = overviewPage.isAtAccountsOverview();
        Assert.assertTrue("Not on the Overview page", isAtOverview); // This will throw an AssertionError if isAtOverview is false
        logger.info("Successfully navigated to the Overview page"); // This will only be reached if isAtOverview is true
    }
}
