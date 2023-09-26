package stepDefinitions;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.LoginPage;
import pages.OverviewPage;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import utils.DriverManager;

public class LoginSteps {

    LoginPage loginPage = new LoginPage();
    OverviewPage overviewPage = new OverviewPage();

    @Given("I am on the Parabank login page")
    public void i_am_on_the_parabank_login_page() {
        loginPage.navigateToLoginPage();
    }



    @When("I enter valid username and password")
    public void i_enter_valid_username_and_password() {
        loginPage.enterUsername("vmirzac");
        loginPage.enterPassword("Endava2023");
    }

    @When("I click on the login button")
    public void i_click_on_the_login_button() {
        loginPage.clickLoginButton();
    }

    @Then("I should be taken to the Overview page")
    public void i_should_be_taken_to_the_overview_page() {
        Assert.assertTrue(overviewPage.isAtAccountsOverview());
    }
}
