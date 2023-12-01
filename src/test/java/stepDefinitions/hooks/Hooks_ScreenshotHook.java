package stepDefinitions.hooks;

import io.cucumber.java.After;
import io.cucumber.java.Scenario;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import stepDefinitions.BaseSteps;
import utils.DriverManager;

public class Hooks_ScreenshotHook extends BaseSteps {

    @After(value = "@ui", order = 1)
    public void grabScreenshot(Scenario scenario) {
        if (scenario.isFailed()) {
            logger.info("UI test failed. Attempting to take a screenshot...");
            WebDriver driver = DriverManager.getDriver();
            byte[] screenshotBytes = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
            scenario.attach(screenshotBytes, "image/png", "Failed Screenshot");
            logger.info("Screenshot taken for failed scenario: {}", scenario.getName());
        } else {
            logger.info("UI test passed. No screenshot required.");
        }
    }
}
