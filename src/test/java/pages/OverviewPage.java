package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class OverviewPage {
    private final WebDriverWait wait;

    @FindBy(xpath = "//h1[contains(text(), 'Accounts Overview')]")
    private WebElement accountsOverviewHeader;

    @FindBy(xpath = "//a[@href='/parabank/overview.htm' and text()='Accounts Overview']")
    private WebElement accountsOverviewButton;


    public OverviewPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.wait = new WebDriverWait(driver, 30L);
    }

    public boolean isAtAccountsOverview() {
        wait.until(ExpectedConditions.visibilityOf(accountsOverviewHeader));
        return accountsOverviewHeader.isDisplayed();
    }

    public boolean isAccountVisible(String accountNumber) {
        String xpath = String.format("//a[contains(text(), '%s')]", accountNumber);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
        return true;
    }

    public void submitAccountOverviewButton() {
        wait.until(ExpectedConditions.elementToBeClickable(accountsOverviewButton)).click();
    }

}