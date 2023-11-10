package stepDefinitions;

import io.cucumber.java.After;
import io.cucumber.java.Scenario;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import utils.DriverManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Hooks_ScreenshotHook {

    private WebDriver driver;
    private static final Logger logger = LoggerFactory.getLogger(Hooks_ScreenshotHook.class);

    @After(value = "@ui", order = 1)
    public void grabScreenshot(Scenario scenario) {
        if (scenario.isFailed()) {
            logger.info("UI test failed. Attempting to take a screenshot...");
            driver = DriverManager.getDriver();
            byte[] screenshotBytes = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
            scenario.attach(screenshotBytes, "image/png", "Failed Screenshot");
            logger.info("Screenshot taken for failed scenario: {}", scenario.getName());
        } else {
            logger.info("UI test passed. No screenshot required.");
        }
    }
}
