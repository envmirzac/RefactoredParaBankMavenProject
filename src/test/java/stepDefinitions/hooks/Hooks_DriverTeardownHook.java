package stepDefinitions.hooks;

import io.cucumber.java.After;
import stepDefinitions.BaseSteps;
import utils.DriverManager;
import utils.ScenarioContext;

public class Hooks_DriverTeardownHook extends BaseSteps {

    @After(order = 0) // This will ensure this hook runs last
    public void tearDown() {
        logger.info("Starting driver and scenario context teardown");
        try {
            DriverManager.quitDriver();
            ScenarioContext.getInstance().clearContext();
            logger.info("Driver and scenario context successfully torn down");
        } catch (Exception e) {
            logger.error("Error during driver and scenario context teardown", e);
        }
    }
}