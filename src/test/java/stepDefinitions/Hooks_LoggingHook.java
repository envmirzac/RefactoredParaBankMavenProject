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
import java.nio.file.Path;
import java.nio.file.Paths;

public class Hooks_LoggingHook {
    private FileAppender<ILoggingEvent> fileAppender;

    @Before
    public void beforeScenario(Scenario scenario) {
        try {
            String featureName = scenario.getUri().getPath();
            featureName = featureName.substring(featureName.lastIndexOf('/') + 1);
            featureName = featureName.replace(".feature", "");

            String logDirectoryPath = "logs";
            Files.createDirectories(Paths.get(logDirectoryPath));

            LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();

            PatternLayout layout = new PatternLayout();
            layout.setContext(loggerContext);
            layout.setPattern("%date %level [%thread] - %msg%n");
            layout.start();

            LayoutWrappingEncoder<ILoggingEvent> encoder = new LayoutWrappingEncoder<>();
            encoder.setLayout(layout);
            encoder.setContext(loggerContext);
            encoder.start();

            fileAppender = new FileAppender<>();
            fileAppender.setContext(loggerContext);
            fileAppender.setName("FEATURE_SPECIFIC_FILE_APPENDER");
            fileAppender.setFile(logDirectoryPath + "/" + featureName + ".log");
            fileAppender.setEncoder(encoder);
            fileAppender.start();

            Logger featureSpecificLogger = loggerContext.getLogger("FEATURE_SPECIFIC_LOGGER." + featureName);
            featureSpecificLogger.addAppender(fileAppender);
            featureSpecificLogger.setAdditive(false); // Do not propagate to root logger
        } catch (Exception e) {
            throw new RuntimeException("Could not set up feature-specific logging", e);
        }
    }

    @After
    public void afterScenario(Scenario scenario) {
        if (fileAppender != null) {
            fileAppender.stop();
            LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
            String featureName = scenario.getUri().getPath();
            featureName = featureName.substring(featureName.lastIndexOf('/') + 1);
            featureName = featureName.replace(".feature", "");
            Logger featureSpecificLogger = loggerContext.getLogger("FEATURE_SPECIFIC_LOGGER." + featureName);
            featureSpecificLogger.detachAppender(fileAppender);
        }
    }
}
