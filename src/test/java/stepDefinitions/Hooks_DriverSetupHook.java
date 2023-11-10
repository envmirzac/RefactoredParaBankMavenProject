package stepDefinitions;

import io.cucumber.java.Before;
import org.openqa.selenium.WebDriver;
import utils.DriverManager;

public class Hooks_DriverSetupHook {

    private WebDriver driver;

    @Before(value = "@ui")
    public void setUp() {
        driver = DriverManager.getDriver();
    }
}
