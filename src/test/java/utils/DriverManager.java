package utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DriverManager {
    private static final Logger logger = LoggerFactory.getLogger(DriverManager.class);
    private static WebDriver driver;
    // private constructor to prevent instantiation from other classes
    private DriverManager() { }

    //The driver is initialized here, the first time it is called. This is "lazy initialization", as the driver is created ONLY when needed(not at application startup)
    // "static and public" ->  allows any part of the application to access the web driver instance. This is a  "global access point"
    public static WebDriver getDriver() {
        if (driver == null) {
            String browserType = PropertiesUtil.getProperty("browser.type");
            boolean headless = Boolean.parseBoolean(PropertiesUtil.getProperty("browser.headless"));

            switch (browserType.toUpperCase()) {
                case "CHROME":
                    System.setProperty("webdriver.chrome.driver", PropertiesUtil.getProperty("webdriver.chrome.driver"));
                    ChromeOptions chromeOptions = new ChromeOptions();
                    if (headless) {
                        chromeOptions.addArguments("--headless");
                    }
                    driver = new ChromeDriver(chromeOptions);
                    logger.info("Chrome browser started");
                    break;
                case "FIREFOX":
                    System.setProperty("webdriver.gecko.driver", PropertiesUtil.getProperty("webdriver.gecko.driver"));
                    FirefoxOptions firefoxOptions = new FirefoxOptions();
                    if (headless) {
                        firefoxOptions.addArguments("--headless");
                    }
                    driver = new FirefoxDriver(firefoxOptions);
                    logger.info("Firefox browser started");
                    break;
                default:
                    logger.error("Unsupported browser type: {}", browserType);
                    throw new IllegalArgumentException("Unsupported browser type: " + browserType);
            }
        }
        return driver;
    }

    public static void quitDriver() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }
}
