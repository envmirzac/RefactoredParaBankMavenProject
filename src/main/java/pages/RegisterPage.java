package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import utils.DriverManager;

public class RegisterPage {
    private WebDriver driver;

    private By registrationHeader = By.xpath("//h1[text()='Registration']");
    private By firstNameField = By.name("firstName");
    private By lastNameField = By.name("lastName");
    private By addressField = By.name("address");
    private By cityField = By.name("city");
    private By stateField = By.name("state");
    private By zipField = By.name("zipCode");
    private By phoneField = By.name("phone");
    private By ssnField = By.name("ssn");
    private By usernameField = By.name("username");
    private By passwordField = By.name("password");
    private By confirmPasswordField = By.name("confirmPassword");
    private By registerButton = By.xpath("//input[@value='Register']");

    public RegisterPage(WebDriver driver) {
        this.driver = driver;
    }

//    public boolean isAt() {
//        return driver.findElement(registrationHeader).isDisplayed();
//    }

    public void goToRegistrationPage() {
        driver.get("https://para.testar.org/parabank/register.htm");  // Replace with the actual URL of the Parabank registration page.
    }

    public void registerUser(String firstName, String lastName, String address, String city, String state, String zip, String phone, String ssn, String username, String password, String confirmPassword) {
        driver.findElement(firstNameField).sendKeys(firstName);
        driver.findElement(lastNameField).sendKeys(lastName);
        driver.findElement(addressField).sendKeys(address);
        driver.findElement(cityField).sendKeys(city);
        driver.findElement(stateField).sendKeys(state);
        driver.findElement(zipField).sendKeys(zip);
        driver.findElement(phoneField).sendKeys(phone);
        driver.findElement(ssnField).sendKeys(ssn);
        driver.findElement(usernameField).sendKeys(username);
        driver.findElement(passwordField).sendKeys(password);
        driver.findElement(confirmPasswordField).sendKeys(confirmPassword);
        driver.findElement(registerButton).click();
    }
}
