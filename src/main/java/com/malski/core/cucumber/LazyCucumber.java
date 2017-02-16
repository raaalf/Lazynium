package com.malski.core.cucumber;

import cucumber.api.CucumberOptions;
import cucumber.api.SnippetType;
import cucumber.api.testng.AbstractTestNGCucumberTests;
import cucumber.api.testng.CucumberFeatureWrapper;
import cucumber.api.testng.TestNGCucumberRunner;
import org.apache.commons.lang3.StringUtils;
import org.testng.IHookCallBack;
import org.testng.IHookable;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

@CucumberOptions(features = "*", glue = "*", tags = "*", format = {"pretty"})
public class LazyCucumber extends AbstractTestNGCucumberTests {
    private TestNGCucumberRunner testNGCucumberRunner;

    public LazyCucumber() {
    }

    @BeforeClass(
            alwaysRun = true
    )
    @Parameters({"features", "glue", "tags", "format", "plugin", "dryRun", "strict", "monochrome", "name", "snippets"})
    public void setUpClass(@Optional("") String features, @Optional("") String glue, @Optional("") String tags,
                           @Optional("") String format, @Optional("") String plugin, @Optional("") String dryRun,
                           @Optional("") String strict, @Optional("") String monochrome, @Optional("") String name,
                           @Optional("") String snippets)
            throws Exception {
        final CucumberOptions cucumberOptionsAnnotation = this.getClass().getAnnotation(CucumberOptions.class);
        Map<String, Object> annotationMap = new HashMap<>();
        if (!StringUtils.isBlank(features)) {
            annotationMap.put("features", features.split(","));
        }
        if (!StringUtils.isBlank(glue)) {
            annotationMap.put("glue", glue.split(","));
        }
        if (!StringUtils.isBlank(tags)) {
            annotationMap.put("tags", tags.split(","));
        }
        if (!StringUtils.isBlank(format)) {
            annotationMap.put("format", format.split(","));
        }
        if (!StringUtils.isBlank(plugin)) {
            annotationMap.put("plugin", plugin.split(","));
        }
        if (!StringUtils.isBlank(dryRun)) {
            annotationMap.put("dryRun", Boolean.getBoolean(dryRun));
        }
        if (!StringUtils.isBlank(strict)) {
            annotationMap.put("strict", Boolean.getBoolean(strict));
        }
        if (!StringUtils.isBlank(monochrome)) {
            annotationMap.put("monochrome", Boolean.getBoolean(monochrome));
        }
        if (!StringUtils.isBlank(name)) {
            annotationMap.put("name", name.split(","));
        }
        if (!StringUtils.isBlank(snippets)) {
            annotationMap.put("snippets", SnippetType.fromString(snippets));
        }
        changeCucumberOptions(cucumberOptionsAnnotation, annotationMap);
        this.testNGCucumberRunner = new TestNGCucumberRunner(this.getClass());
    }

    @Test(description = "Runs Cucumber Feature", dataProvider = "features")
    public void feature(CucumberFeatureWrapper cucumberFeature) {
        this.testNGCucumberRunner.runCucumber(cucumberFeature.getCucumberFeature());
    }

    @DataProvider
    public Object[][] features() {
        return this.testNGCucumberRunner.provideFeatures();
    }

    @AfterClass
    public void tearDownClass() throws Exception {
        this.testNGCucumberRunner.finish();
    }

    @SuppressWarnings("unchecked")
    private static void changeCucumberOptions(Annotation annotation, Map<String, Object> annotationMap) {
        Object handler = Proxy.getInvocationHandler(annotation);
        Field f;
        try {
            f = handler.getClass().getDeclaredField("memberValues");
        } catch (NoSuchFieldException | SecurityException e) {
            throw new IllegalStateException(e);
        }
        f.setAccessible(true);
        Map<String, Object> memberValues;
        try {
            memberValues = (Map<String, Object>) f.get(handler);
        } catch (IllegalArgumentException | IllegalAccessException e) {
            throw new IllegalStateException(e);
        }
        annotationMap.keySet().forEach(key -> memberValues.put(key, annotationMap.get(key)));
    }
}