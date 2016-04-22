package com.malski.core.cucumber;

import com.malski.core.web.Browser;
import cucumber.api.CucumberOptions;
import cucumber.api.testng.TestNGCucumberRunner;
import org.testng.IHookCallBack;
import org.testng.IHookable;
import org.testng.ITestResult;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

@CucumberOptions(features = "*", glue = "*", tags = "*", format = {"pretty"})
public class CucumberNG implements IHookable {
//public class CucumberNG {
    public CucumberNG() {
    }

    @Parameters({"browser", "features", "glue", "tags", "format"})
    @Test(description = "Runs Cucumber Features")
    public void runCukes(@Optional("chrome") String browser, @Optional("*") String features, @Optional("*") String glue,
                    @Optional("") String tags, @Optional("pretty") String format)
            throws IOException {
        TestContext.setBrowser(new Browser(browser));
        final CucumberOptions cucumberOptionsAnnotation = CucumberNG.class.getAnnotation(CucumberOptions.class);
        Map<String, String[]> annotationMap = new HashMap<String, String[]>() {{
            put("features", features.split(";"));
            put("glue", glue.split(";"));
            put("tags", tags.split(";"));
            put("format", format.split(";"));
        }};
        changeCucumberOptions(cucumberOptionsAnnotation, annotationMap);
        TestNGCucumberRunner runner = new TestNGCucumberRunner(this.getClass());
        runner.runCukes();
    }

    public void run(IHookCallBack iHookCallBack, ITestResult iTestResult) {
        iHookCallBack.runTestMethod(iTestResult);
    }

//    @Parameters({"browser", "features", "glue", "tags", "format"})
//    @Test(description = "Runs Cucumber Features")
//    public void runCukes(@Optional("chrome") String browser, @Optional("*") String features, @Optional("*") String glue,
//                    @Optional("") String tags, @Optional("pretty") String format)
//            throws IOException {
//        System.setProperty("TEST_BROWSER", browser); // TODO change it to not use system properties
//        final CucumberOptions cucumberOptionsAnnotation = CucumberNG.class.getAnnotation(CucumberOptions.class);
//        Map<String, String[]> annotationMap = new HashMap<String, String[]>() {{
//            put("features", features.split(";"));
//            put("glue", glue.split(";"));
//            put("tags", tags.split(";"));
//            put("format", format.split(";"));
//        }};
//        changeCucumberOptions(cucumberOptionsAnnotation, annotationMap);
//        new TestNGCucumberRunner(getClass()).runCukes();
//    }

    @SuppressWarnings("unchecked")
    private static void changeCucumberOptions(Annotation annotation, Map<String, String[]> annotationMap) {
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