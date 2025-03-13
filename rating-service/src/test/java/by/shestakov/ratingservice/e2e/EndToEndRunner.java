package by.shestakov.ratingservice.e2e;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/java/by/shestakov/ratingservice/resources/feature"
)
public class EndToEndRunner {


}
