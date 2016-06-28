package com.malski.core.cucumber;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        format = {},
        features = "",
        glue = {""},
        tags = {""}
)
public class JUnitRunner {
}
