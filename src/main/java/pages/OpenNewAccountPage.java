package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class OpenNewAccountPage {
    private final WebDriver driver;

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

    public OpenNewAccountPage(WebDriver driver) {
        this.driver = driver;
        // This method initializes the WebElements declared in the class (those with the @FindBy annotations).
        PageFactory.initElements(driver, this);
    }

    public void selectAccountType(String accountType) {
        WebDriverWait wait = new WebDriverWait(driver, 30L);
        wait.until(ExpectedConditions.visibilityOf(accountTypeDropdown));
        Select dropdown = new Select(accountTypeDropdown);
        dropdown.selectByVisibleText(accountType);
    }

    public void selectFromAccount(String accountNumber) {
        WebDriverWait wait = new WebDriverWait(driver, 30L);
        wait.until(ExpectedConditions.visibilityOf(fromAccountDropdown));
        Select dropdown = new Select(fromAccountDropdown);
        dropdown.selectByVisibleText(accountNumber);
    }

    public void submitNewAccountButton() {
        WebDriverWait wait = new WebDriverWait(driver,30L);
        wait.until(ExpectedConditions.elementToBeClickable(openNewAccountButton)).click();
    }

    public String getConfirmationMessage() {
        WebDriverWait wait = new WebDriverWait(driver, 30L);
        return wait.until(ExpectedConditions.visibilityOf(confirmationMessage)).getText();
    }

    public void navigateToOpenNewAccountPage() {
        WebDriverWait wait = new WebDriverWait(driver, 30L);
        wait.until(ExpectedConditions.elementToBeClickable(openNewAccountLink)).click();
    }

    public void clickLogoutButton() {
        WebDriverWait wait = new WebDriverWait(driver, 30L);
        wait.until(ExpectedConditions.elementToBeClickable(logoutButton)).click();
    }
}
