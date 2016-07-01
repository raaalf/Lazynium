package com.malski.core.web.base;

import com.malski.core.cucumber.TestContext;
import com.malski.core.web.conditions.WaitConditions;
import io.github.bonigarcia.wdm.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.MarionetteDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import static org.openqa.selenium.support.ui.ExpectedConditions.*;

/**
 * Browser class is implementation of WebDriver to execute basic actions using it
 */
public class Browser extends LazySearchContextImpl implements WebDriver, LazySearchContext {
    private WebDriver driver;
    private JsExecutor jsExecutor;
    private ScreenShooter shooter;
    private Actions actions;
    private FluentWait<WebDriver> wait;
    private String browserType;

    private static final long TIMEOUT_SECONDS = 30;
    private static final long IMPLICITLY_TIMEOUT_SECONDS = 10;

    protected final Logger log = Logger.getLogger(getClass());

    public Browser(String browserType) {
        this.browserType = browserType.toLowerCase();
        initialize();
    }

    public JsExecutor getJsExecutor() {
        return this.jsExecutor;
    }

    public ScreenShooter getScreenShooter() {
        return this.shooter;
    }

    public Actions getActions() {
        return this.actions;
    }

    @Override
    public void refresh() {
        if (getWebDriver().toString().contains("null")) {
            initialize();
        }
    }

    private void initialize() {
        driver = initializeWebDriver(this.browserType);
        jsExecutor = new JsExecutor(driver);
        shooter = new ScreenShooter(driver);
        actions = new Actions(driver);
        setDefaultWaitTimeout();
        setSearchContext(driver);
    }

    @Override
    public SearchContext getSearchContext() {
        if (super.getSearchContext() == null) {
            setSearchContext(getWebDriver());
        }
        return super.getSearchContext();
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
        getWebDriver().get(url);
        waitForPageToLoad();
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

    @Override
    public String getWindowHandle() {
        return getWebDriver().getWindowHandle();
    }

    @Override
    public TargetLocator switchTo() {
        return getWebDriver().switchTo();
    }

    public void switchToNewWindow() {
        List<String> handles = new ArrayList<>(getWindowHandles());
        switchTo().window(handles.get(handles.size() - 1));
    }

    public void switchToNextWindow() {
        List<String> handles = new ArrayList<>(getWindowHandles());
        int indexOfCurrentPage = handles.indexOf(getWindowHandle());
        if (indexOfCurrentPage > -1 && indexOfCurrentPage < handles.size() - 1) {
            switchTo().window(handles.get(indexOfCurrentPage + 1));
        }
    }

    public void switchToPreviousWindow() {
        List<String> handles = new ArrayList<>(getWindowHandles());
        int indexOfCurrentPage = handles.indexOf(getWindowHandle());
        if (indexOfCurrentPage > 0)
            switchTo().window(handles.get(indexOfCurrentPage - 1));
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

    public void setImplicitlyWait(long time, TimeUnit timeUnit) {
        getWebDriver().manage().timeouts().implicitlyWait(time, timeUnit);
    }

    public FluentWait<WebDriver> getWait() {
        return wait;
    }

    public FluentWait<WebDriver> getWait(long seconds) {
        setWaitTimeout(seconds);
        return wait;
    }

    public void setDefaultWaitTimeout() {
        this.wait = new WebDriverWait(getWebDriver(), TIMEOUT_SECONDS);
        setImplicitlyWait(IMPLICITLY_TIMEOUT_SECONDS, TimeUnit.SECONDS);
    }

    public void setWaitTimeout(long seconds) {
        this.wait = new WebDriverWait(getWebDriver(), seconds);
    }

    public void waitForPageToLoad() {
        try {
            getWait().until(WaitConditions.pageLoaded(getWebDriver()));
        } catch (TimeoutException | NullPointerException e) {
            //sometimes PageValidator remains in Interactive mode and never becomes Complete, then we can still try to access the controls
            String state = getJsExecutor().getReadyState();
            if (!state.equalsIgnoreCase("interactive")) throw e;
        } catch (WebDriverException e) {
            String state = getJsExecutor().getReadyState();
            if (!(state.equalsIgnoreCase("complete") || state.equalsIgnoreCase("loaded"))) throw e;
        }
    }

    public boolean elementExists(WebElement element, long timeout) {
        WebDriverWait wait = new WebDriverWait(getWebDriver(), timeout);
        try {
            wait.until(ExpectedConditions.visibilityOf(element));
        } catch (TimeoutException e) {
            return false;
        }
        return true;
    }

    public boolean waitUntilNotPresent(By by) {
        setImplicitlyWait(100, TimeUnit.MILLISECONDS);
        try {
            findElement(by);
        } catch (TimeoutException e) {
            return true;
        } finally {
            setDefaultWaitTimeout();
        }
        return false;
    }

    public void waitUntilPresent(By by) {
        getWait().until(presenceOfElementLocated(by));
    }

    public void waitUntilPresent(By by, long timeout) {
        getWait(timeout).until(presenceOfElementLocated(by));
    }

    public void waitUntilVisible(By by) {
        getWait().until(visibilityOfElementLocated(by));
    }

    public void waitUntilVisible(By by, long timeout) {
        getWait(timeout).until(visibilityOfElementLocated(by));
    }

    public void waitUntilDisappear(By by) {
        getWait().until(invisibilityOfElementLocated(by));
    }

    public void waitUntilDisappear(By by, long timeout) {
        getWait(timeout).until(invisibilityOfElementLocated(by));
    }

    public void waitUntilEnabled(By by) {
        getWait().until(elementToBeClickable(by));
    }

    public void waitUntilEnabled(By by, long timeout) {
        getWait(timeout).until(elementToBeClickable(by));
    }

    public void waitUntilDisabled(By by) {
        getWait().until(not(elementToBeClickable(by)));
    }

    public void waitUntilDisabled(By by, long timeout) {
        getWait(timeout).until(not(elementToBeClickable(by)));
    }

    public void waitUntilAttributeChange(By by, String attributeName, String expectedValue) {
        getWait().until(WaitConditions.attributeChanged(by, attributeName, expectedValue));
    }

    public void waitUntilAttributeChange(By by, String attributeName, String expectedValue, long timeout) {
        getWait(timeout).until(WaitConditions.attributeChanged(by, attributeName, expectedValue));
    }


    public Alert getActiveAlert() {
        return getWebDriver().switchTo().alert();
    }

    public boolean isAlertPresent() {
        try {
            getWait(0).until(ExpectedConditions.alertIsPresent());
            return true;
        } catch (TimeoutException eTO) {
            return false;
        }
    }

    private WebDriver initializeWebDriver(String browser) {
        try {
            WebDriver driver;
            switch (browser) {
                case "chrome":
                case "ch":
                    try {
                        ChromeDriverManager.getInstance().setup();
                    } catch (Exception e) {
                        System.setProperty("webdriver.chrome.driver", getDriversDirPath() + "chromedriver.exe");
                    }
                    driver = new ChromeDriver();
                    break;
                case "firefox":
                case "ff":
                    driver = new FirefoxDriver();
                    break;
                case "edge":
                    EdgeDriverManager.getInstance().setup();
                    driver = new EdgeDriver();
                    break;
                case "internet_explorer":
                case "ie":
                    InternetExplorerDriverManager.getInstance().setup();
                    driver = new InternetExplorerDriver();
                    break;
                case "marionette":
                    MarionetteDriverManager.getInstance().setup();
                    driver = new MarionetteDriver();
                    break;
                case "phantom_js":
                default:
                    PhantomJsDriverManager.getInstance().setup();
                    driver = new PhantomJSDriver();
            }
            moveBrowserToOtherDisplay(driver);
            driver.manage().window().maximize();
            driver.manage().timeouts().implicitlyWait(IMPLICITLY_TIMEOUT_SECONDS, TimeUnit.SECONDS);
            return driver;
        } catch (IllegalArgumentException ex) {
            return null;
        }
    }

    private void moveBrowserToOtherDisplay(WebDriver driver) {
        String multiScreen = TestContext.getPropertyByKey("seleniumDisplay");
        if (!StringUtils.isBlank(multiScreen)) {
            Point point;
            switch (multiScreen.toLowerCase()) {
                case "right":
                    point = new Point(2200, 0);
                    break;
                case "left":
                    point = new Point(-1200, 0);
                    break;
                case "up":
                    point = new Point(0, -1200);
                    break;
                case "down":
                    point = new Point(0, 1200);
                    break;
                default:
                    point = new Point(0, 0);
            }
            driver.manage().window().setPosition(point);
        }
    }

    private String getDriversDirPath() {
        return System.getProperty("user.dir") + String.format("%1$ssrc%1$smain%1$sresources%1$sdrivers%1$s", File.separator);
    }
}