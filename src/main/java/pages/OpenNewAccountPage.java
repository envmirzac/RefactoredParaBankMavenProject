package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;

public class OpenNewAccountPage {
    private WebDriver driver;

    private By accountTypeDropdown = By.id("type");
    private By fromAccountDropdown = By.id("fromAccountId");
    private By openNewAccountButton = By.xpath("//input[@type='submit']");
    private By confirmationMessage = By.className("title");

    public OpenNewAccountPage(WebDriver driver) {
        this.driver = driver;
    }

    public void selectAccountType(String accountType) {
        new Select(driver.findElement(accountTypeDropdown)).selectByVisibleText(accountType);
    }

    public void selectFromAccount(String accountNumber) {
        new Select(driver.findElement(fromAccountDropdown)).selectByVisibleText(accountNumber);
    }

    public void clickOpenNewAccount() {
        driver.findElement(openNewAccountButton).click();
    }

    public String getConfirmationMessage() {
        return driver.findElement(confirmationMessage).getText();
    }
}
