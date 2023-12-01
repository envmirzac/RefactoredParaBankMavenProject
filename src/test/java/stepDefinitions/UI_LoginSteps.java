package stepDefinitions;

import pages.LoginPage;
import utils.DriverManager;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import utils.PropertiesUtil;

public class UI_LoginSteps extends BaseSteps {

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
