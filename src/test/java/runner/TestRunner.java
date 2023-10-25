package runner;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features",
        glue = "stepDefinitions",
        monochrome = true,
        tags = "@Run or @SmokeTests and not @ignore",  // Corrected syntax
        plugin = {
                "pretty",
                "html:target/reports/Reports.html",
                "json:target/reports/Cucumber.json"
        }
)
public class TestRunner {
}
