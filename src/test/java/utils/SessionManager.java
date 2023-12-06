package utils;

import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SessionManager {

    private static final Logger logger = LoggerFactory.getLogger(SessionManager.class);
    private static Session session;

    public static void openSession() {
        try {
            if (session == null || !session.isOpen()) {
                session = HibernateUtil.getSessionFactory().openSession();
                logger.info("Database session successfully opened.");
            }
        } catch (Exception e) {
            logger.error("Failed to open database session: {}", e.getMessage());
        }
    }

    public static void closeSession() {
        try {
            if (session != null && session.isOpen()) {
                session.close();
                logger.info("Database session successfully closed.");
            }
        } catch (Exception e) {
            logger.error("Failed to close database session: {}", e.getMessage());
        }
    }

    public static Session getSession() {
        return session;
    }
}
