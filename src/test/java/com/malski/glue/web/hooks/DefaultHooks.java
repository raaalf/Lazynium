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
        TestContext.setBrowser(TestContext.config().driver());
        log.info("###### Running scenario on browser: " + TestContext.config().driver());
        if (TestContext.config().isVideoRecording()) {
            TestContext.browser().initVideoRecorder();
            TestContext.browser().videoRecorder().start(scenario.getName());
        }
    }

    @Before(order = 2)
    public void deleteAllCookies() {
        TestContext.browser().manage().deleteAllCookies();
    }

    @After(order = 2)
    public void afterScenario(Scenario scenario) {
        if (scenario.isFailed()) {
            try {
                byte[] screenshot = TestContext.browser().screenShooter().getScreenshotAsByteArray();
                scenario.embed(screenshot, "image/png");
            } catch (WebDriverException somePlatformsDontSupportScreenshots) {
                log.error(somePlatformsDontSupportScreenshots.getMessage());
            }
            //get page alert if exist
            if (TestContext.browser().isAlertPresent()) {
                log.error("There was an alert on page: " + TestContext.browser().activeAlert().getText());
            }
        }
    }

    @After(order = 1)
    public void quitBrowser(Scenario scenario) {
        //quit browser
        if (TestContext.config().isVideoRecording()) {
            addVideoToReport(scenario);
        }
        if (TestContext.browser() != null) {
            TestContext.browser().quit();
        }
    }

    private void addVideoToReport(Scenario scenario) {
        TestContext.browser().videoRecorder().stop();
        if(TestContext.config().isVideoRecordingOnFail() && !scenario.isFailed()) {
            TestContext.browser().videoRecorder().removeVideo();
        } else {
            String videoPath =  TestContext.browser().videoRecorder().getVideoFilePath();
            String mimeType =  TestContext.browser().videoRecorder().getMimeType();
            scenario.embed(videoPath.getBytes(), mimeType);
        }
    }
}
