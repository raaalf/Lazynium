package com.malski.core.web.control;

import com.malski.core.web.conditions.WaitConditions;
import com.malski.core.web.factory.BrowserFactory;
import org.apache.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.internal.BuildInfo;
import org.openqa.selenium.internal.WrapsDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.*;
import java.util.concurrent.TimeUnit;

import static com.malski.core.utils.TestContext.config;


/**
 * Browser class is implementation of WebDriver to execute basic actions using it
 */
public class Browser implements WebDriver, BrowserWait, WrapsDriver, LazySearchContext {
    private WebDriver driver;
    private JsExecutor jsExecutor;
    private ScreenShooter shooter;
    private Actions actions;
    private String browserType;
    private String latestWindowHandle;
    private VideoRecorder videoRecorder;

    protected final Logger log = Logger.getLogger(getClass());

    public Browser(String browserType) {
        this.browserType = browserType.toLowerCase();
        initialize();
    }

    public JsExecutor jsExecutor() {
        if(this.jsExecutor == null) {
            this.jsExecutor = new JsExecutor(getWrappedDriver());
        }
        return this.jsExecutor;
    }

    public void initVideoRecorder() {
        this.videoRecorder = new VideoRecorder(getWrappedDriver());
    }

    public VideoRecorder videoRecorder() {
        return this.videoRecorder;
    }

    public ScreenShooter screenShooter() {
        if(this.shooter == null) {
            this.shooter = new ScreenShooter(getWrappedDriver());
        }
        return this.shooter;
    }

    public Actions actions() {
        if(this.actions == null) {
            this.actions = new Actions(getWrappedDriver());
        }
        return this.actions;
    }

    @Override
    public boolean refresh() {
        if (getWrappedDriver().toString().contains("null")) {
            initialize();
            return true;
        }
        return false;
    }

    private void initialize() {
        initialize(BrowserFactory.initializeDriver(this.browserType));
    }

    private void initialize(WebDriver driver) {
        this.driver = driver;
        this.jsExecutor = null;
        this.shooter = null;
        this.actions = null;
        setImplicitlyWait(config().implicitlyTimeoutMs(), TimeUnit.MILLISECONDS);
    }

    @Override
    public SearchContext searchContext() {
        return getWrappedDriver();
    }

    @Override
    public List<WebElement> findElements(By by) {
        return LazySearchContext.super.findElements(by);
    }

    @Override
    public WebElement findElement(By by) {
        return LazySearchContext.super.findElement(by);
    }

    @Override
    public String getPageSource() {
        return getWrappedDriver().getPageSource();
    }

    @Override
    public void get(String url) {
        getWrappedDriver().get(url);
    }

    public void navigateTo(String url) {
        get(url);
        waitUntilPageLoaded();
    }

    @Override
    public String getCurrentUrl() {
        return getWrappedDriver().getCurrentUrl();
    }

    @Override
    public String getTitle() {
        return getWrappedDriver().getTitle();
    }

    @Override
    public void close() {
        getWrappedDriver().close();
    }

    @Override
    public void quit() {
        getWrappedDriver().quit();
    }

    @Override
    public Set<String> getWindowHandles() {
        return getWrappedDriver().getWindowHandles();
    }

    public int windowsCount() {
        return getWrappedDriver().getWindowHandles().size();
    }

    @Override
    public String getWindowHandle() {
        return getWrappedDriver().getWindowHandle();
    }

    @Override
    public TargetLocator switchTo() {
        return getWrappedDriver().switchTo();
    }

    public void switchToNewWindow() {
        latestWindowHandle = getWindowHandle();
        for (String winHandle : getWindowHandles()) {
            switchTo().window(winHandle);
        }
    }

    public void switchToNextWindow() {
        List<String> handles = Arrays.asList(getWindowHandles().toArray(new String[getWindowHandles().size() - 1]));
        int indexOfCurrentPage = handles.indexOf(getWindowHandle());
        if (indexOfCurrentPage < handles.size() - 1) {
            switchTo().window(handles.get(indexOfCurrentPage + 1));
        }
    }

    public void switchToPreviousWindow() {
        switchTo().window(latestWindowHandle);
    }

    @Override
    public Navigation navigate() {
        return getWrappedDriver().navigate();
    }

    @Override
    public Options manage() {
        return getWrappedDriver().manage();
    }

    @Override
    public WebDriver getWrappedDriver() {
        return driver;
    }

    public RemoteWebDriver remoteDriver() {
        return (RemoteWebDriver) getWrappedDriver();
    }

    public void setImplicitlyWait(long time, TimeUnit timeUnit) {
        getWrappedDriver().manage().timeouts().implicitlyWait(time, timeUnit);
    }

    @Override
    public FluentWait<WebDriver> getWait() {
        return new WebDriverWait(getWrappedDriver(), config().explicitlyTimeout(), config().driverSleepMs());
    }

    @Override
    public FluentWait<WebDriver> getWait(long seconds) {
        return new WebDriverWait(getWrappedDriver(), seconds, config().driverSleepMs());
    }

    @Override
    public void waitUntilPageLoaded(long timePageLoad) {
        try {
            if(isAlertPresent()) {
                return; // alert opened so page is loaded
            }
            getWait(timePageLoad).until(WaitConditions.pageLoaded());
        } catch (TimeoutException | NullPointerException e) {
            String state = jsExecutor().getReadyState();
            if (!state.equalsIgnoreCase("interactive")) {
                throw e;
            }
        } catch (WebDriverException e) {
            String state = jsExecutor().getReadyState();
            if (!(state.equalsIgnoreCase("complete") || state.equalsIgnoreCase("loaded"))) {
                throw e;
            }
        }
    }

    public boolean isDisplayed(By by) {
        return $e(by).isDisplayed();
    }

    public boolean isDisplayed(By by, long timeout) {
        return $e(by).isDisplayed(timeout);
    }

    public boolean isVisible(By by) {
        return $e(by).isVisible();
    }

    public boolean isVisible(By by, long timeout) {
        return $e(by).isVisible(timeout);
    }

    public boolean isPresent(By by) {
        return $e(by).isPresent();
    }

    public boolean isPresent(By by, long timeout) {
        return $e(by).isPresent(timeout);
    }

    public boolean isEnabled(By by) {
        return $e(by).isEnabled();
    }

    public boolean isEnabled(By by, long timeout) {
        return $e(by).isEnabled(timeout);
    }

    public boolean hasFocus(By by) {
        return $e(by).hasFocus();
    }

    public boolean hasFocus(By by, long timeout) {
        return $e(by).hasFocus(timeout);
    }

    public boolean isInViewport(By by) {
        return $e(by).isInViewport();
    }

    public boolean isInViewport(By by, long timeout) {
        return $e(by).isInViewport(timeout);
    }


    public Alert activeAlert() {
        return getWrappedDriver().switchTo().alert();
    }

    public boolean isAlertPresent() {
        try {
            getWrappedDriver().switchTo().alert();
            return true;
        } catch (NoAlertPresentException ignore) {
            return false;
        }
    }

    public Capabilities capabilities() {
        return remoteDriver().getCapabilities();
    }

    @SuppressWarnings("unchecked")
    public Map<String, String> systemInfo() {
        Map<String, String> systemInfo = new HashMap<>();
        Capabilities cap = capabilities();
        systemInfo.put("browser_name", cap.getBrowserName());
        systemInfo.put("browser_version", browserVersion(cap));
        systemInfo.put("selenium_version", new BuildInfo().getReleaseLabel());
        try {
            systemInfo.put("driver_version", ((Map<String, String>) cap.getCapability(cap.getBrowserName())).get(cap.getBrowserName() + "driverVersion"));
        } catch (Exception ignore) {
            systemInfo.put("driver_version", "");
        }
        systemInfo.put("os_name", System.getProperty("os.name"));
        systemInfo.put("os_version", System.getProperty("os.version"));
        systemInfo.put("os_arch", System.getProperty("os.arch"));
        systemInfo.put("java_version", System.getProperty("java.version"));
        return systemInfo;
    }

    private String browserVersion(Capabilities cap) {
        String version;
        if ("internet explorer".equalsIgnoreCase(cap.getBrowserName())) {
            String uAgent = jsExecutor().executeScript("return navigator.userAgent;");
            System.out.println(uAgent);
            if (uAgent.contains("MSIE") && uAgent.contains("Windows")) {
                version = uAgent.substring(uAgent.indexOf("MSIE") + 5, uAgent.indexOf("Windows") - 2);
            } else if (uAgent.contains("Trident/7.0")) {
                version = "11.0";
            } else {
                version = "0.0";
            }
        } else {
            version = cap.getVersion();
        }
        return version;
    }
}