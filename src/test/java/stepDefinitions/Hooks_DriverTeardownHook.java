package stepDefinitions;

import io.cucumber.java.After;
import utils.DriverManager;
import utils.HibernateUtil;
import utils.ScenarioContext;
import utils.HibernateUtil;

public class Hooks_DriverTeardownHook {

    @After(order = 0) // This will ensure this hook runs last
    public void tearDown() {
        DriverManager.quitDriver();
        ScenarioContext.getInstance().clearContext();
    }

}
