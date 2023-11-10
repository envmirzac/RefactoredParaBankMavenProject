package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import utils.ScenarioContext;

public class OverviewPage {

    private final ScenarioContext scenarioContext = ScenarioContext.getInstance();
    String newAccOnOpenNewAccPage = scenarioContext.getValueFromScenarioContext("New_Account");
    private final WebDriver driver;

    @FindBy(xpath = "//h1[contains(text(), 'Accounts Overview')]")
    private WebElement accountsOverviewHeader;

    @FindBy(xpath = "//a[@href='/parabank/overview.htm' and text()='Accounts Overview']")
    private WebElement accountsOverviewButton;


    public OverviewPage(WebDriver driver) {
        this.driver = driver;
        // This method initializes the WebElements declared in the class (those with the @FindBy annotations).
        PageFactory.initElements(driver, this);
    }

    public boolean isAtAccountsOverview() {
        WebDriverWait wait = new WebDriverWait(driver, 30L);
        // Wait for the Accounts Overview header to be visible on the page
        wait.until(ExpectedConditions.visibilityOf(accountsOverviewHeader));
        // Now, verify whether the header is displayed on the page
        return accountsOverviewHeader.isDisplayed();
    }

    public boolean isAccountVisible(String accountNumber) {
        String xpath = String.format("//a[contains(text(), '%s')]", accountNumber);
        // Waiting for the element with the account number to be present in the DOM.
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
        // If the element is found and visible, the account number is confirmed to be present on the page.
        return true;
    }

    public void submitAccountOverviewButton() {
        WebDriverWait wait = new WebDriverWait(driver,30L);
        wait.until(ExpectedConditions.elementToBeClickable(accountsOverviewButton)).click();
    }

}
