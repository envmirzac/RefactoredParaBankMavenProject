package stepDefinitions;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.FileAppender;
import ch.qos.logback.core.encoder.LayoutWrappingEncoder;
import ch.qos.logback.classic.PatternLayout;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.slf4j.LoggerFactory;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Hooks_LoggingHook {
    // Variable to hold the file appender that will be used for logging
    private FileAppender<ILoggingEvent> fileAppender;

    @Before
    public void beforeScenario(Scenario scenario) {
        try {
            // Get the feature file's name from the scenario's URI
            String featureName = scenario.getUri().getPath();
            featureName = featureName.substring(featureName.lastIndexOf('/') + 1);
            featureName = featureName.replace(".feature", "");

            // Define the directory path where log files will be stored
            String logDirectoryPath = "target/logs/featureLogs";
            // Create the directory if it does not exist
            Files.createDirectories(Paths.get(logDirectoryPath));

            // Obtain the current logger context
            LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();

            // Set up a pattern layout to format log messages
            PatternLayout layout = new PatternLayout();
            layout.setContext(loggerContext);
            // This is the way the logs should look like : 12:34:56.789 INFO [main] - This is a sample message
            layout.setPattern("%date %level [%thread] - %msg%n");
            layout.start();

            // An "encoder" translates the log events into a byte stream after they are formatted, which can then be
            // written to a file, console, or any other destination defined by the appender.
            LayoutWrappingEncoder<ILoggingEvent> encoder = new LayoutWrappingEncoder<>();
            encoder.setLayout(layout);
            encoder.setContext(loggerContext);
            encoder.start();

            // Initialize the file appender with the encoder and file path
            fileAppender = new FileAppender<>();
            fileAppender.setContext(loggerContext);
            fileAppender.setName("FEATURE_SPECIFIC_FILE_APPENDER");
            fileAppender.setFile(logDirectoryPath + "/" + featureName + ".log");
            fileAppender.setEncoder(encoder);
            fileAppender.start();

            // Get the root logger and attach the new file appender to it
            Logger rootLogger = (Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
            rootLogger.addAppender(fileAppender);
        } catch (Exception e) {
            // If there's an error during the setup, throw a runtime exception
            throw new RuntimeException("Could not set up feature-specific logging", e);
        }
    }

    @After
    public void afterScenario(Scenario scenario) {
        // If the file appender was used, then stop and remove it
        if (fileAppender != null) {
            fileAppender.stop(); // Stop the file appender to close the file
            LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
            String featureName = scenario.getUri().getPath();
            featureName = featureName.substring(featureName.lastIndexOf('/') + 1);
            featureName = featureName.replace(".feature", "");
            // Get the logger specific to the feature based on its name
            Logger featureSpecificLogger = loggerContext.getLogger("FEATURE_SPECIFIC_LOGGER." + featureName);
            // Remove the file appender from the feature-specific logger
            featureSpecificLogger.detachAppender(fileAppender);
            // Clean up by stopping all appenders associated with this logger
            featureSpecificLogger.detachAndStopAllAppenders();
        }
    }
}
