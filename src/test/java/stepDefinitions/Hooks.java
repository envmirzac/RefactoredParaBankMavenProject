//package stepDefinitions;
//
//import io.cucumber.java.After;
//import io.cucumber.java.Before;
//import io.cucumber.java.Scenario;
//import org.openqa.selenium.OutputType;
//import org.openqa.selenium.TakesScreenshot;
//import org.openqa.selenium.WebDriver;
//import utils.DriverManager;
//import utils.ScenarioContext;  // Ensure to import ScenarioContext
//
//public class Hooks {
//
//    private WebDriver driver;
//
//    @Before
//    public void setUp() {
//        driver = DriverManager.getDriver();
//    }
//
//    @After
//    public void grabScreenshot(Scenario scenario) {
//        if (scenario.isFailed()) {
//            byte[] screenshotBytes = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
//            scenario.attach(screenshotBytes, "image/png", "Failed Screenshot");
//        }
//
//
//        DriverManager.quitDriver();
////TODO
//// Clear the ScenarioContext after each scenario
//// to learn Single Responsibility Principle
//// To split in 3 different hooks
//// UI scenarios to be specific for certain hooks
//        ScenarioContext.getInstance().clearContext();
//    }
//}
