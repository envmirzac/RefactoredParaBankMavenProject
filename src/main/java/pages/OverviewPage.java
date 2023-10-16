package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class OverviewPage {

    private final WebDriver driver;

    @FindBy(linkText = "Open New Account")
    private WebElement openNewAccountLink;

    @FindBy(xpath = "//h1[text()='Accounts Overview']")
    private WebElement accountsOverviewHeader;

    public OverviewPage(WebDriver driver) {
        this.driver = driver;
        // This method initializes the WebElements declared in the class (those with the @FindBy annotations).
        PageFactory.initElements(driver, this);
    }

    public boolean isAtAccountsOverview() {
        return accountsOverviewHeader.isDisplayed();
    }
}
