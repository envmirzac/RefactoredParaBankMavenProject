package stepDefinitions.hooks;

import io.cucumber.java.After;
import stepDefinitions.BaseDefine;
import utils.ScenarioContext;

public class ScenarioContextHook extends BaseDefine {

    @After(order = 0) // This will ensure this hook runs after the WebDriver teardown
    public void clearScenarioContext() {
        logger.info("Starting Scenario Context teardown");
        try {
            ScenarioContext.getInstance().clearContext();
            logger.info("Scenario Context successfully cleared");
        } catch (Exception e) {
            logger.error("Error during Scenario Context teardown", e);
        }
    }
}
