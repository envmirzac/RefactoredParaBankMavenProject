package utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertiesUtil {
    private static final Properties properties = new Properties();

    static {
        try {
            // Adjusted path to use forward slashes
            String propertiesFilePath = System.getProperty("user.dir") + "/src/test/java/utils/config.properties";
            FileInputStream fileInputStream = new FileInputStream(propertiesFilePath);
            properties.load(fileInputStream);
        } catch (IOException e) {
            throw new RuntimeException("Failed to read properties from config.properties file.", e);
        }
    }

    public static String getProperty(String key) {
        return properties.getProperty(key);
    }
}
