package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import utils.DriverManager;

public class HomePage {
    private WebDriver driver = DriverManager.getDriver();
    private By registerLink = By.linkText("Register");

    public RegisterPage clickRegisterLink() {
        driver.findElement(registerLink).click();
        return new RegisterPage(driver);  // Here, pass the driver as an argument
    }
}
