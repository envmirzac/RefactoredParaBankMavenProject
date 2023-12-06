package stepDefinitions.hooks;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.SessionManager;

public class DbConnectionsHook {

    private static final Logger logger = LoggerFactory.getLogger(DbConnectionsHook.class);

    @Before(value = "@db", order = 0)
    public void openDatabaseConnection(Scenario scenario) {
        logger.info("Opening database session for scenario: {}", scenario.getName());
        SessionManager.openSession();
    }

    @After(value = "@db", order = 1)
    public void closeDatabaseConnections(Scenario scenario) {
        logger.info("Closing database session for scenario: {}", scenario.getName());
        SessionManager.closeSession();
    }
}
