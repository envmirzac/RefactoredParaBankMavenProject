package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.DriverManager;

public class LoginPage {
    private WebDriver driver = DriverManager.getDriver();
    private By usernameField = By.name("username");
    private By passwordField = By.name("password");
    private By loginButton = By.xpath("//input[@value='Log In']");

    public void navigateToLoginPage() {
        driver.get("http://para.testar.org/parabank/index.htm");
// JavascriptExecutor js = (JavascriptExecutor) driver; // Keeping this line commented as it's not used
//        js.executeScript("window.location='https://para.testar.org/parabank/index.htm?ConnType=JDBC';");
    }

    public void enterUsername(String username) {
        driver.findElement(usernameField).sendKeys(username);
    }

    public void enterPassword(String password) {
        driver.findElement(passwordField).sendKeys(password);
    }

    public void clickLoginButton() {
        driver.findElement(loginButton).click();
        // Removed return statement as OverviewPage is not used in this context.
    }
}
