package com.malski.core.web.control;

import com.malski.core.web.conditions.WaitConditions;
import com.malski.core.web.factory.BrowserFactory;
import org.apache.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.internal.BuildInfo;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.*;
import java.util.concurrent.TimeUnit;

import static com.malski.core.utils.TestContext.getConfig;


/**
 * Browser class is implementation of WebDriver to execute basic actions using it
 */
public class Browser implements LazySearchContext, WebDriver, BrowserWait {
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
            this.jsExecutor = new JsExecutor(getWebDriver());
        }
        return this.jsExecutor;
    }

    public void initVideoRecorder() {
        this.videoRecorder = new VideoRecorder(getWebDriver());
    }

    public VideoRecorder videoRecorder() {
        return this.videoRecorder;
    }

    public ScreenShooter screenShooter() {
        if(this.shooter == null) {
            this.shooter = new ScreenShooter(getWebDriver());
        }
        return this.shooter;
    }

    public Actions actions() {
        if(this.actions == null) {
            this.actions = new Actions(getWebDriver());
        }
        return this.actions;
    }

    @Override
    public boolean refresh() {
        if (getWebDriver().toString().contains("null")) {
            initialize();
            return true;
        }
        return false;
    }

    private void initialize() {
        initialize(BrowserFactory.initializeWebDriver(this.browserType));
    }

    private void initialize(WebDriver driver) {
        this.driver = driver;
        this.jsExecutor = null;
        this.shooter = null;
        this.actions = null;
        setImplicitlyWait(getConfig().getImplicitlyTimeoutMs(), TimeUnit.MILLISECONDS);
    }

    @Override
    public SearchContext getContext() {
        return getWebDriver();
    }

    @Override
    public String getPageSource() {
        return getWebDriver().getPageSource();
    }

    @Override
    public void get(String url) {
        getWebDriver().get(url);
    }

    public void navigateTo(String url) {
        get(url);
        waitUntilPageLoaded();
    }

    @Override
    public String getCurrentUrl() {
        return getWebDriver().getCurrentUrl();
    }

    @Override
    public String getTitle() {
        return getWebDriver().getTitle();
    }

    @Override
    public void close() {
        getWebDriver().close();
    }

    @Override
    public void quit() {
        getWebDriver().quit();
    }

    @Override
    public Set<String> getWindowHandles() {
        return getWebDriver().getWindowHandles();
    }

    public int getWindowsCount() {
        return getWebDriver().getWindowHandles().size();
    }

    @Override
    public String getWindowHandle() {
        return getWebDriver().getWindowHandle();
    }

    @Override
    public TargetLocator switchTo() {
        return getWebDriver().switchTo();
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
        return getWebDriver().navigate();
    }

    @Override
    public Options manage() {
        return getWebDriver().manage();
    }

    public WebDriver getWebDriver() {
        return driver;
    }

    public RemoteWebDriver getRemoteDriver() {
        return (RemoteWebDriver) getWebDriver();
    }

    public void setImplicitlyWait(long time, TimeUnit timeUnit) {
        getWebDriver().manage().timeouts().implicitlyWait(time, timeUnit);
    }

    @Override
    public FluentWait<WebDriver> getWait() {
        return new WebDriverWait(getWebDriver(), getConfig().getExplicitlyTimeout(), getConfig().getDriverSleepMs());
    }

    @Override
    public FluentWait<WebDriver> getWait(long seconds) {
        return new WebDriverWait(getWebDriver(), seconds, getConfig().getDriverSleepMs());
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
        return getElement(by).isDisplayed();
    }

    public boolean isDisplayed(By by, long timeout) {
        return getElement(by).isDisplayed(timeout);
    }

    public boolean isVisible(By by) {
        return getElement(by).isVisible();
    }

    public boolean isVisible(By by, long timeout) {
        return getElement(by).isVisible(timeout);
    }

    public boolean isPresent(By by) {
        return getElement(by).isPresent();
    }

    public boolean isPresent(By by, long timeout) {
        return getElement(by).isPresent(timeout);
    }

    public boolean isEnabled(By by) {
        return getElement(by).isEnabled();
    }

    public boolean isEnabled(By by, long timeout) {
        return getElement(by).isEnabled(timeout);
    }

    public boolean hasFocus(By by) {
        return getElement(by).hasFocus();
    }

    public boolean hasFocus(By by, long timeout) {
        return getElement(by).hasFocus(timeout);
    }

    public boolean isInViewport(By by) {
        return getElement(by).isInViewport();
    }

    public boolean isInViewport(By by, long timeout) {
        return getElement(by).isInViewport(timeout);
    }


    public Alert getActiveAlert() {
        return getWebDriver().switchTo().alert();
    }

    public boolean isAlertPresent() {
        try {
            getWebDriver().switchTo().alert();
            return true;
        } catch (NoAlertPresentException ignore) {
            return false;
        }
    }

    public Capabilities getCapabilities() {
        return getRemoteDriver().getCapabilities();
    }

    @SuppressWarnings("unchecked")
    public Map<String, String> getSystemInfo() {
        Map<String, String> systemInfo = new HashMap<>();
        Capabilities cap = getCapabilities();
        systemInfo.put("browser_name", cap.getBrowserName());
        systemInfo.put("browser_version", getBrowserVersion(cap));
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

    private String getBrowserVersion(Capabilities cap) {
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