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
        if(TestContext.getConfig().isVideoRecordingEnabled()) {
            TestContext.getVideoRecorder().start(scenario.getName());
        }
        TestContext.setBrowser(TestContext.getConfig().getDriver());
        log.info("###### Running scenario on browser: " + TestContext.getConfig().getDriver());
    }

    @Before(order = 2)
    public void deleteAllCookies() {
        TestContext.getBrowser().manage().deleteAllCookies();
    }

    @After(order = 2)
    public void afterScenario(Scenario scenario) {
        if (scenario.isFailed()) {
            try {
                byte[] screenshot = TestContext.getBrowser().getScreenShooter().getScreenshotAsByteArray();
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
    public void quitBrowser() {
        //quit browser
        TestContext.getBrowser().quit();
        TestContext.setBrowser(null);
        if(TestContext.getConfig().isVideoRecordingEnabled()) {
            TestContext.getVideoRecorder().stop();
        }
    }
}
