package stepDefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import org.junit.Assert;
import pages.OpenNewAccountPage;
import utils.DriverManager;

public class OpenNewAccountPageSteps {
    private OpenNewAccountPage openNewAccountPage = new OpenNewAccountPage(DriverManager.getDriver());

    @Given("I am on the Parabank open new account page")
    public void i_am_on_the_parabank_open_new_account_page() {
        DriverManager.getDriver().get("https://para.testar.org/parabank/openaccount.htm"); // Update with the correct URL if different
    }

    @When("I select the account type")
    public void i_select_the_account_type() {
        openNewAccountPage.selectAccountType("CHECKING"); // Update with the correct account type if different
    }

    @When("I select the from account")
    public void i_select_the_from_account() {
        openNewAccountPage.selectFromAccount("106362"); // Update with the correct from account if different
    }

    @When("I click on the open new account button")
    public void i_click_on_the_open_new_account_button() {
        openNewAccountPage.clickOpenNewAccount();
    }

    @Then("I should see a new account confirmation message")
    public void i_should_see_a_new_account_confirmation_message() {
        Assert.assertEquals("Account Opened Successfully!", openNewAccountPage.getConfirmationMessage()); // Update with the correct confirmation message if different
    }
}
