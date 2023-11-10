package stepDefinitions;

import io.cucumber.java.After;
import io.cucumber.java.Scenario;
import utils.HibernateUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Hooks_DbConnectionsHook {

    private static final Logger logger = LoggerFactory.getLogger(Hooks_DbConnectionsHook.class);

    @After(value = "@db", order = 1) //This order ensures that the database is closed before the driver is quit, but after any other database-related operations.
    public void closeDatabaseConnections(Scenario scenario) {
        logger.info("Closing database connections for scenario: {}", scenario.getName());
        try {
            HibernateUtil.closeDbConnections();
            logger.info("Database connections successfully closed.");
        } catch (Exception e) {
            logger.error("Failed to close database connections: {}", e.getMessage());
        }
    }

}
