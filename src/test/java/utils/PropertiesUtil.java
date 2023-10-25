package utils;
import java.util.Properties;
import java.io.InputStream;
import java.io.IOException;

public class PropertiesUtil {
    // 'private' means it's only accessible within this class, and 'static' means it's associated with the class, not instances of the class.
    private static final Properties properties = new Properties();

    // This block (static) is executed when the class is loaded into memory, before any methods are called or new objects are created.
    static {
        // The try-with-resources block automatically closes the 'InputStream' object once it's no longer needed,
        try (InputStream inputStream = PropertiesUtil.class.getClassLoader().getResourceAsStream("config.properties")) {
            properties.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to read config.properties file.");
        }
    }

    // 'static' means the method is associated with the class, not instances.
    public static String getProperty(String key) {
        // retrieves the property value corresponding to the passed key from the 'Properties' object.
        return properties.getProperty(key);
    }
}
