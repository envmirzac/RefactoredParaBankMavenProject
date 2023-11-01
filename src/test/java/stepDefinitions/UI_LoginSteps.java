package stepDefinitions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.junit.Assert;
import pages.LoginPage;
import pages.OverviewPage;
import utils.DriverManager;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import utils.PropertiesUtil;

public class UI_LoginSteps {

    private static final Logger logger = LoggerFactory.getLogger(UI_LoginSteps.class);

    // Instantiate LoginPage with the WebDriver from DriverManager class
    LoginPage loginPage = new LoginPage(DriverManager.getDriver());

    @Given("I am on the Parabank login page")
    public void i_am_on_the_parabank_login_page() {
        loginPage.navigateToLoginPage();
        logger.info("Navigated to the Parabank login page");
    }

    @When("I enter valid username and password")
    public void i_enter_valid_username_and_password() {
        String username = PropertiesUtil.getProperty("username");
        String password = PropertiesUtil.getProperty("password");
        loginPage.enterUsername(username);
        loginPage.enterPassword(password);
        logger.info("Entered username and password from config file");
    }


    @When("I click on the login button")
    public void i_click_on_the_login_button() {
        loginPage.clickLoginButton();
        logger.info("Clicked on the login button");
    }

    }