package stepDefinitions;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.junit.Assert;
import pages.OverviewPage;
import utils.DriverManager;
import utils.ScenarioContext;

public class UI_OverviewSteps {

    private static final Logger logger = LoggerFactory.getLogger(UI_OverviewSteps.class); // Corrected the logger class reference
    private final OverviewPage overviewPage = new OverviewPage(DriverManager.getDriver());
    private final ScenarioContext scenarioContext = ScenarioContext.getInstance();

    @And("the account {string} is visible on the Account Overview page")
    public void theAccountIsVisibleOnTheAccountOverviewPage(String accountNumber) {
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

    @And("navigation to the account overview is initiated")
    public void navigationToTheAccountOverviewIsInitiated() {
        overviewPage.submitAccountOverviewButton();
    }

    @Then("the Overview page should be displayed")
    public void the_overview_page_should_be_displayed() {
        boolean isAtOverview = overviewPage.isAtAccountsOverview();
        Assert.assertTrue("Not on the Overview page", isAtOverview); // This will throw an AssertionError if isAtOverview is false
        logger.info("Successfully navigated to the Overview page"); // This will only be reached if isAtOverview is true
    }

    @And("the new account number is visible on the Account Overview page")
    public void theNewAccountNumberIsVisibleOnTheAccountOverviewPage() {
        String newAccountNumber = (String) ScenarioContext.getInstance().getValueFromScenarioContext("New_Account");
        Assert.assertTrue("The new account number is not visible on the Overview page",
                overviewPage.isAccountVisible(newAccountNumber));
        logger.info("New account number {} is visible on the Overview page", newAccountNumber);
    }
}
