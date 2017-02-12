package com.malski.glue.web.hooks;

import com.malski.core.utils.TestContext;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriverException;

public class DefaultHooks {
    private static Logger log = Logger.getLogger(DefaultHooks.class);

    public DefaultHooks() {
        TestContext.initialize();
    }

    @Before(order = 1)
    public void init(Scenario scenario) {
        TestContext.setBrowser(TestContext.getConfig().getDriver());
        log.info("###### Running scenario on browser: " + TestContext.getConfig().getDriver());
        if (TestContext.getConfig().isVideoRecording()) {
            TestContext.getBrowser().initVideoRecorder();
            TestContext.getBrowser().videoRecorder().start(scenario.getName());
        }
    }

    @Before(order = 2)
    public void deleteAllCookies() {
        TestContext.getBrowser().manage().deleteAllCookies();
    }

    @After(order = 2)
    public void afterScenario(Scenario scenario) {
        if (scenario.isFailed()) {
            try {
                byte[] screenshot = TestContext.getBrowser().screenShooter().getScreenshotAsByteArray();
                scenario.embed(screenshot, "image/png");
            } catch (WebDriverException somePlatformsDontSupportScreenshots) {
                log.error(somePlatformsDontSupportScreenshots.getMessage());
            }
            //get page alert if exist
            if (TestContext.getBrowser().isAlertPresent()) {
                log.error("There was an alert on page: " + TestContext.getBrowser().getActiveAlert().getText());
            }
        }
    }

    @After(order = 1)
    public void quitBrowser(Scenario scenario) {
        //quit browser
        if (TestContext.getConfig().isVideoRecording()) {
            addVideoToReport(scenario);
        }
        if (TestContext.getBrowser() != null) {
            TestContext.getBrowser().quit();
        }
    }

    private void addVideoToReport(Scenario scenario) {
        TestContext.getBrowser().videoRecorder().stop();
        if(TestContext.getConfig().isVideoRecordingOnFail() && !scenario.isFailed()) {
            TestContext.getBrowser().videoRecorder().removeVideo();
        } else {
            String videoPath =  TestContext.getBrowser().videoRecorder().getVideoFilePath();
            String mimeType =  TestContext.getBrowser().videoRecorder().getMimeType();
            scenario.embed(videoPath.getBytes(), mimeType);
        }
    }
}
