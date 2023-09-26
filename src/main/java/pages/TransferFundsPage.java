package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;
import utils.DriverManager;

public class TransferFundsPage {
    private WebDriver driver = DriverManager.getDriver();

    // Elements
    private By sourceAccountDropdown = By.id("fromAccountId");
    private By destinationAccountDropdown = By.id("toAccountId");
    private By amountInputField = By.name("input");
    private By transferButton = By.className("button");
    private By confirmationMessage = By.xpath("//div[contains(text(), 'Transfer Confirmation')]");

    // Navigate to Transfer Funds page (Assuming you have the URL, if not this method can be adjusted)
    public void navigateToTransferFundsPage() {
        driver.get("https://para.testar.org/parabank/transfer.htm"); // Replace with the correct URL
    }

    public void selectSourceAccount(String account) {
        Select dropdown = new Select(driver.findElement(sourceAccountDropdown));
        dropdown.selectByVisibleText(account);
    }

    public void selectDestinationAccount(String account) {
        Select dropdown = new Select(driver.findElement(destinationAccountDropdown));
        dropdown.selectByVisibleText(account);
    }

    public void enterTransferAmount(String amount) {
        driver.findElement(amountInputField).sendKeys(amount);
    }

    public void clickTransferButton() {
        driver.findElement(transferButton).click();
    }

    public String getConfirmationMessage() {
        return driver.findElement(confirmationMessage).getText();
    }
}
