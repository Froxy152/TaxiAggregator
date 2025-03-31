package by.shestakov.ridesservice.e2e;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/java/by/shestakov/ridesservice/resource/feature"
)
public class EndToEndRunner {
}
