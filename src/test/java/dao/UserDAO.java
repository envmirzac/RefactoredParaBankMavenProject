package dao;

import model.User;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.SessionManager; // import the SessionManager

public class UserDAO {

    private static final Logger logger = LoggerFactory.getLogger(UserDAO.class);

    public void saveUser(User user) {
        Transaction transaction = null;
        Session session = SessionManager.getSession();
        try {
            transaction = session.beginTransaction();
            session.save(user);
            transaction.commit();
            logger.info("User saved successfully: {}", user.getUsername());
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
                logger.error("Transaction rolled back due to error while saving user: {}", user.getUsername());
            }
            logger.error("Error saving user: {}", user.getUsername(), e);
        }
    }

    public User getUserByUsername(String username) {
        Session session = SessionManager.getSession();
        try {
            User user = session.createQuery("FROM User WHERE username = :username", User.class)
                    .setParameter("username", username)
                    .uniqueResult();
            logger.info("Retrieved user by username: {}", username);
            return user;
        } catch (Exception e) {
            logger.error("Error retrieving user by username: {}", username, e);
            return null; // If an exception occurs, returns null (no user was found)
        }
    }
}
