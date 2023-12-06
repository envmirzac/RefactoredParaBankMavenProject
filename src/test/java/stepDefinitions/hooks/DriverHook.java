package stepDefinitions.hooks;

import io.cucumber.java.Before;
import io.cucumber.java.After;
import stepDefinitions.BaseDefine;
import utils.DriverManager;

public class DriverHook extends BaseDefine {
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

    @After(value = "@ui", order = 1) // Order set to 1 to ensure WebDriver teardown happens before scenario context teardown
    public void tearDown() {
        logger.info("Starting WebDriver teardown");
        try {
            DriverManager.quitDriver();
            logger.info("WebDriver successfully torn down");
        } catch (Exception e) {
            logger.error("Error during WebDriver teardown", e);
        }
    }
}
