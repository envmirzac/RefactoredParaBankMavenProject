package stepDefinitions.hooks;

import io.cucumber.java.After;
import io.cucumber.java.Scenario;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import stepDefinitions.BaseDefine;
import utils.DriverManager;

public class ScreenshotHook extends BaseDefine {

    @After(value = "@ui", order = 0)
    public void grabScreenshot(Scenario scenario) {
        if (scenario.isFailed()) {
            logger.info("UI test failed. Attempting to take a screenshot...");
            WebDriver driver = DriverManager.getDriver();
            byte[] screenshotBytes = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
            // Save screenshot without a report. ""Failed Screenshot"" specify a different name for each screenshot
            scenario.attach(screenshotBytes, "image/png", "Failed Screenshot");
            logger.info("Screenshot taken for failed scenario: {}", scenario.getName());
        } else {
            logger.info("UI test passed. No screenshot required.");
        }
    }
}
