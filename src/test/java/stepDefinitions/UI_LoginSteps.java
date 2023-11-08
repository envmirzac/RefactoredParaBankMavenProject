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

    @Given("the Parabank login page is displayed")
    public void the_parabank_login_page_is_displayed() {
        loginPage.navigateToLoginPage();
        logger.info("Navigated to the Parabank login page");
    }

    @When("valid credentials are entered")
    public void valid_credentials_are_entered() {
        String username = PropertiesUtil.getProperty("username");
        String password = PropertiesUtil.getProperty("password");
        loginPage.enterUsername(username);
        loginPage.enterPassword(password);
        logger.info("Entered username and password from config file");
    }


    @When("the login button is clicked")
    public void the_login_button_is_clicked() {
        loginPage.clickLoginButton();
        logger.info("Clicked on the login button");
    }

    }
