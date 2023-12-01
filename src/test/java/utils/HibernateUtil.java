package utils;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HibernateUtil {

    // A private static variable to hold the Hibernate SessionFactory.
    private final static SessionFactory sessionFactory;
    private static final Logger logger = LoggerFactory.getLogger(HibernateUtil.class);

    // A static initializer block. It runs once when the class is loaded.
    static {
        try {
            // Configure Hibernate with settings from hibernate.cfg.xml and then build a SessionFactory.
            sessionFactory = new Configuration().configure().buildSessionFactory();
            logger.info("SessionFactory created successfully.");
        } catch (Throwable ex) {
            // Catch any exceptions and throw an initialization error. This means there was an error creating a SessionFactory.
            logger.error("Failed to create sessionFactory object.", ex);
            throw new ExceptionInInitializerError(ex);

        }
    }

    //"static" allows it to be called without an instance of HibernateUtil, providing access to the Singleton "SessionFactory" instance.
    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    }


