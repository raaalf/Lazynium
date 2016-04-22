package com.malski.google.runners;

import cucumber.api.CucumberOptions;
import cucumber.api.testng.AbstractTestNGCucumberTests;

@CucumberOptions(features = "src/test/resources/features",
        glue = "com.malski.google.glue",
        tags = "@search",
        format = {"pretty"})
public class GoogleTestRunner extends AbstractTestNGCucumberTests {
}