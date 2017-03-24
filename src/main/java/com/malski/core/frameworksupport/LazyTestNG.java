package com.malski.core.frameworksupport;

import com.malski.core.utils.TestContext;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriverException;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;

@Listeners({ com.example.MyListener.class, com.example.MyMethodInterceptor.class })
public abstract class LazyTestNG {
    private Logger log = Logger.getLogger(getClass());

    @BeforeClass(alwaysRun = true)
    public void init(ITestContext testContext) {
        TestContext.setBrowser(TestContext.config().driver());
        log.info("###### Running scenario on browser: " + TestContext.config().driver());
        if (TestContext.config().isVideoRecording()) {
            TestContext.browser().initVideoRecorder();
            TestContext.browser().videoRecorder().start(testContext.getName());
        }
    }

    @BeforeClass(alwaysRun = true, dependsOnMethods={"init"})
    public void deleteAllCookies() {
        TestContext.browser().manage().deleteAllCookies();
    }

    @AfterClass(alwaysRun = true, dependsOnMethods={"quitBrowser"})
    public void afterScenario(ITestResult result) {
        if (!result.isSuccess()) {
            try {
                byte[] screenshot = TestContext.browser().screenShooter().getScreenshotAsByteArray();
                result.embed(screenshot, "image/png");
            } catch (WebDriverException somePlatformsDontSupportScreenshots) {
                log.error(somePlatformsDontSupportScreenshots.getMessage());
            }
            //get page alert if exist
            if (TestContext.browser().isAlertPresent()) {
                log.error("There was an alert on page: " + TestContext.browser().activeAlert().getText());
            }
        }
    }

    @AfterClass(alwaysRun = true)
    public void quitBrowser(ITestResult result) {
        //quit browser
        if (TestContext.config().isVideoRecording()) {
            addVideoToReport(result);
        }
        if (TestContext.browser() != null) {
            TestContext.browser().quit();
        }
    }

    private void addVideoToReport(ITestResult result) {
        TestContext.browser().videoRecorder().stop();
        if(TestContext.config().isVideoRecordingOnFail() && result.isSuccess()) {
            TestContext.browser().videoRecorder().removeVideo();
        } else {
            String videoPath =  TestContext.browser().videoRecorder().getVideoFilePath();
            String mimeType =  TestContext.browser().videoRecorder().getMimeType();
            result.embed(videoPath.getBytes(), mimeType);
        }
    }
}
