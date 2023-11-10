package utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesUtil {
    private static final Logger logger = LoggerFactory.getLogger(PropertiesUtil.class);
    private static final Properties properties = new Properties();

    static {
        // InputStream is the "resource" that is managed by the try-with-resources statement. It is an object that implements the "AutoCloseable interface",
        // which means it has a close() method that will be called automatically when the try block is exited, whether the operations within it execute
        // normally or if an exception is thrown (no "finally" needed)
        try (InputStream inputStream = PropertiesUtil.class.getClassLoader().getResourceAsStream("config.properties")) {
            if (inputStream == null) {
                throw new IOException("Property file 'config.properties' not found in the classpath");
            }
            properties.load(inputStream);
        } catch (IOException e) {
            logger.error("Failed to read config.properties file.", e);
            // Re-throw the exception as a RuntimeException
            throw new RuntimeException("Failed to read config.properties file.", e);
        }
    }

    public static String getProperty(String key) {
        return properties.getProperty(key);
    }
}
