package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import utils.DriverManager;

public class OverviewPage {
    private WebDriver driver = DriverManager.getDriver();
    private By openNewAccountLink = By.linkText("Open New Account");
    private By accountsOverviewHeader = By.xpath("//h1[text()='Accounts Overview']");

    public OpenNewAccountPage clickOpenNewAccountLink() {
        driver.findElement(openNewAccountLink).click();
        return new OpenNewAccountPage(driver); // Pass the driver to the constructor
    }

    public boolean isAtAccountsOverview() {
        return driver.findElement(accountsOverviewHeader).isDisplayed();
    }
}
