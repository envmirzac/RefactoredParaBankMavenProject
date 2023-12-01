package stepDefinitions.hooks;

import io.cucumber.java.Before;
import stepDefinitions.BaseSteps;
import utils.DriverManager;

public class Hooks_DriverSetupHook extends BaseSteps {

    @Before(value = "@ui")
    public void setUp() {
        logger.info("Initializing WebDriver for UI testing");
        try {
            DriverManager.getDriver();
            logger.info("WebDriver initialized successfully");
        } catch (Exception e) {
            logger.error("Error during WebDriver initialization", e);
        }
    }
}
