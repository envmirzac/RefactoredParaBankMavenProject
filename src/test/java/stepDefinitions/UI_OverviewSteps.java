package stepDefinitions;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import org.junit.Assert;
import pages.OverviewPage;
import utils.DriverManager;
import utils.ScenarioContext;
import utils.ScenarioContextKeys;

public class UI_OverviewSteps extends BaseDefine {

    private final OverviewPage overviewPage = new OverviewPage(DriverManager.getDriver());

    @And("the account {string} is visible on the Account Overview page")
    public void theAccountIsVisibleOnTheAccountOverviewPage(String accountNumber) {
        boolean isVisible = overviewPage.isAccountVisible(accountNumber);
        if (isVisible) {
            logger.info("Account number {} is visible on the Account Overview page", accountNumber);
        } else {
            logger.error("Account number {} is NOT visible on the Account Overview page", accountNumber);
        }

        Assert.assertTrue("The expected account number is not visible on the Overview page.", isVisible);
    }

    @And("navigation to the account overview is initiated")
    public void navigationToTheAccountOverviewIsInitiated() {
        overviewPage.submitAccountOverviewButton();
    }

    @Then("the Overview page should be displayed")
    public void the_overview_page_should_be_displayed() {
        boolean isAtOverview = overviewPage.isAtAccountsOverview();
        logger.info("Successfully navigated to the Overview page"); // This will only be reached if isAtOverview is true
        Assert.assertTrue("Not on the Overview page", isAtOverview); // This will throw an AssertionError if isAtOverview is false
    }

    @And("the new account number is visible on the Account Overview page")
    public void theNewAccountNumberIsVisibleOnTheAccountOverviewPage() {
        String newAccountNumber = scenarioContext.getValueFromScenarioContext(ScenarioContextKeys.ScenarioContextKey.NEW_ACCOUNT);
        logger.info("New account number {} is visible on the Overview page", newAccountNumber);
        Assert.assertTrue("The new account number is not visible on the Overview page",
                overviewPage.isAccountVisible(newAccountNumber));
    }
}
