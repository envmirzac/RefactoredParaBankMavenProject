package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import utils.PropertiesUtil;


public class LoginPage {

    private final WebDriver driver;

    // Using @FindBy to declare WebElements
    @FindBy(name = "username")
    private WebElement usernameField;

    @FindBy(name = "password")
    private WebElement passwordField;

    @FindBy(xpath = "//input[@value='Log In']")
    private WebElement loginButton;

    // This is the constructor for the LoginPage class.
    public LoginPage(WebDriver driver) {
        this.driver = driver;
        // This method initializes the WebElements declared in the class (those with the @FindBy annotations).
        PageFactory.initElements(driver, this);
    }

    public void navigateToLoginPage() {
        String baseUrl = PropertiesUtil.getProperty("baseUrl");
        driver.get(baseUrl);
    }

    public void enterUsername(String username) {
        WebDriverWait wait = new WebDriverWait(driver, 30L); // Adjust time as needed
        wait.until(ExpectedConditions.elementToBeClickable(usernameField));
        usernameField.clear();
        usernameField.sendKeys(username);
    }

    public void enterPassword(String password) {
        WebDriverWait wait = new WebDriverWait(driver, 30L); // Adjust time as needed
        wait.until(ExpectedConditions.elementToBeClickable(passwordField));
        passwordField.clear();
        passwordField.sendKeys(password);
    }

    public void clickLoginButton() {
        WebDriverWait wait = new WebDriverWait(driver, 30L); // Adjust time as needed
        wait.until(ExpectedConditions.elementToBeClickable(loginButton)).click();
    }
}
