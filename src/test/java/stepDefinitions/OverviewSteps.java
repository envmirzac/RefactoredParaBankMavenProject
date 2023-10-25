package stepDefinitions;

import io.cucumber.java.en.And;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.junit.Assert;
import pages.OverviewPage;
import utils.DriverManager;

public class OverviewSteps {

    private static final Logger logger = LoggerFactory.getLogger(OverviewSteps.class); // Corrected the logger class reference
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
}
