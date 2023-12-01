package stepDefinitions.hooks;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.hibernate.Session;
import utils.HibernateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Hooks_DbConnectionsHook {

    private static final Logger logger = LoggerFactory.getLogger(Hooks_DbConnectionsHook.class);
    private static Session session;

    @Before(value = "@db", order = 0) // Open database connection before scenarios tagged with @db
    public void openDatabaseConnection(Scenario scenario) {
        logger.info("Opening database connection for scenario: {}", scenario.getName());
        try {
            if (session == null || !session.isOpen()) {
                session = HibernateUtil.getSessionFactory().openSession();
                logger.info("Database connection successfully opened.");
            }
        } catch (Exception e) {
            logger.error("Failed to open database connection: {}", e.getMessage());
        }
    }

    @After(value = "@db", order = 1) // Close database connection after scenarios tagged with @db
    public void closeDatabaseConnections(Scenario scenario) {
        logger.info("Closing database connections for scenario: {}", scenario.getName());
        try {
            if (session != null && session.isOpen()) {
                session.close();
                logger.info("Database connection successfully closed.");
            }
        } catch (Exception e) {
            logger.error("Failed to close database connection: {}", e.getMessage());
        }
    }

    public static Session getSession() {
        return session;
    }
}
