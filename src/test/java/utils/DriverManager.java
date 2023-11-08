package utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

public class DriverManager {
    private static WebDriver driver;

    public enum BrowserType {
        CHROME,
        FIREFOX
    }
    // private constructor to prevent instantiation from other classes
    // specific for Singleton
    private DriverManager() { }

    public static WebDriver getDriver(BrowserType browserType, boolean headless) {
        if (driver == null) {
            // synchronized block to prevent multi-threading issues
            // By using the synchronized block, if two threads try to instantiate the driver at the same time,
            // one will acquire the lock and proceed, while the other will have to wait. Once the first thread exits
            // the synchronized block, the second thread will enter and see that the driver is no longer null,
            // thus it won't create a new instance.
            //TODO to remove synchronized
            // to study multithreading.
            synchronized (DriverManager.class) {  // specific for Singleton Pattern
                if (driver == null) {
                    switch (browserType) {
                        case CHROME:
                            //To move to props
                            System.setProperty("webdriver.chrome.driver", "src/test/resources/drivers/chromedriver.exe");
                            ChromeOptions chromeOptions = new ChromeOptions();
                            //To move to props
                            if (headless) {
                                chromeOptions.addArguments("--headless");
                            }
                            driver = new ChromeDriver(chromeOptions);
                            break;
                        case FIREFOX:
                            System.setProperty("webdriver.gecko.driver", "src/test/resources/drivers/geckodriver.exe");
                            FirefoxOptions firefoxOptions = new FirefoxOptions();
                            if (headless) {
                                firefoxOptions.addArguments("--headless");
                            }
                            // cum se poate de avut aceeasi conditie pentru acelasi browser (diferite versiuni)
                            driver = new FirefoxDriver(firefoxOptions);
                            break;
                    }
                }
            }
        }
        return driver;
    }

    // Overloaded getDriver method for using default values
    public static WebDriver getDriver() {
        return getDriver(BrowserType.FIREFOX, true);
    }

    public static void quitDriver() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }
}
