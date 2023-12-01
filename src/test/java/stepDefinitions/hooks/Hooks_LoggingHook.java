package stepDefinitions.hooks;
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
    // Variable to hold the file appender for logging
    private FileAppender<ILoggingEvent> fileAppender;

    @Before
    public void beforeScenario(Scenario scenario) {
        try {
            // Extract feature file name from the scenario's URI
            String featureName = scenario.getUri().getPath();
            featureName = featureName.substring(featureName.lastIndexOf('/') + 1);
            featureName = featureName.replace(".feature", "");

            String logDirectoryPath = "target/logs/featureLogs";
            Files.createDirectories(Paths.get(logDirectoryPath));

            // Obtain the current logger context
            LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();

            // Set up a pattern layout for formatting log messages (will look like this: "2023-11-17 14:30:00 INFO [main] - This is a log message")
            PatternLayout layout = new PatternLayout();
            layout.setContext(loggerContext);
            layout.setPattern("%date %level [%thread] - %msg%n");
            layout.start();

            // Setup encoder to translate log events into a byte stream
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

            // Get root logger and attach new file appender
            Logger rootLogger = (Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
            rootLogger.addAppender(fileAppender);
        } catch (Exception e) {
            // Throw runtime exception if setup fails
            throw new RuntimeException("Could not set up feature-specific logging", e);
        }
    }

    @After
    public void afterScenario(Scenario scenario) {
        // Check if the file appender is used
        if (fileAppender != null) {
            fileAppender.stop(); // Stop the file appender to close the file

            LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
            String featureName = scenario.getUri().getPath();
            featureName = featureName.substring(featureName.lastIndexOf('/') + 1);
            featureName = featureName.replace(".feature", "");
            // Get logger for the specific feature
            Logger featureSpecificLogger = loggerContext.getLogger("FEATURE_SPECIFIC_LOGGER." + featureName);
            // Detach the file appender from the feature-specific logger
            featureSpecificLogger.detachAppender(fileAppender);
            // Clean up by stopping all appenders associated with this logger
            featureSpecificLogger.detachAndStopAllAppenders();
        }
    }
}
