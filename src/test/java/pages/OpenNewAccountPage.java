package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class OpenNewAccountPage {
    private final WebDriverWait wait;

    @FindBy(xpath = "//select[starts-with(@class, 'input')]")
    private WebElement accountTypeDropdown;

    @FindBy(xpath = "//select[@id='fromAccountId']")
    private WebElement fromAccountDropdown;

    @FindBy(xpath = "//a[@href='/parabank/openaccount.htm' and text()='Open New Account']")
    private WebElement openNewAccountLink;

    @FindBy(xpath = "//a[@href='/parabank/logout.htm' and text()='Log Out']")
    private WebElement logoutButton;

    @FindBy(xpath = "//input[@type='submit']")
    private WebElement openNewAccountButton;

    @FindBy(xpath = "//p[text()='Congratulations, your account is now open.']")
    private WebElement confirmationMessage;

    @FindBy(xpath = "//a[@id='newAccountId']")
    private WebElement newAccountNumber;

    public OpenNewAccountPage(WebDriver driver) {
        // This method initializes the WebElements declared in the class (those with the @FindBy annotations).
        PageFactory.initElements(driver, this);
        this.wait = new WebDriverWait(driver, 30L);
    }

    public void selectAccountType(String accountType) {
        wait.until(ExpectedConditions.visibilityOf(accountTypeDropdown));
        Select dropdown = new Select(accountTypeDropdown);
        dropdown.selectByVisibleText(accountType);
    }

    public void selectFromAccount(String accountNumber) {
        wait.until(ExpectedConditions.visibilityOf(fromAccountDropdown));
        Select dropdown = new Select(fromAccountDropdown);
        dropdown.selectByVisibleText(accountNumber);
    }

    public void submitNewAccountButton() {
        wait.until(ExpectedConditions.elementToBeClickable(openNewAccountButton)).click();
    }

    public String getConfirmationMessage() {
        return wait.until(ExpectedConditions.visibilityOf(confirmationMessage)).getText();
    }

    public void navigateToOpenNewAccountPage() {
        wait.until(ExpectedConditions.elementToBeClickable(openNewAccountLink)).click();
    }

    public String getNewAccountNumber (){
        return wait.until(ExpectedConditions.visibilityOf(newAccountNumber)).getText();
    }
}
