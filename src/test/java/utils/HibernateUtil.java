package utils;
// SessionFactory is used to create sessions for database interactions.
import org.hibernate.SessionFactory;
// Configuration is used to configure and bootstrap Hibernate.
import org.hibernate.cfg.Configuration;

public class HibernateUtil {

    // A private static variable to hold the Hibernate SessionFactory. Being static means there's only one instance for this entire class.
    private final static SessionFactory sessionFactory;

    // A static initializer block. It runs once when the class is loaded.
    static {
        try {
            // Configure Hibernate with settings from hibernate.cfg.xml and then build a SessionFactory.
            sessionFactory = new Configuration().configure().buildSessionFactory();
        } catch (Throwable ex) {
            // Catch any exceptions and throw an initialization error. This means there was an error creating a SessionFactory.
            throw new ExceptionInInitializerError(ex);
        }
    }

    // Public method to get the created SessionFactory.
    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
    public static void closeDbConnections() {
        if (sessionFactory != null) {
            sessionFactory.close();
        }
    }
}

