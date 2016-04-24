package com.malski.core.web;

import com.malski.core.web.conditions.WaitConditions;
import com.malski.core.web.elements.Element;
import com.malski.core.web.elements.ElementImpl;
import com.malski.core.web.elements.Elements;
import com.malski.core.web.elements.ElementsImpl;
import com.malski.core.web.factory.LazyLocator;
import io.github.bonigarcia.wdm.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.MarionetteDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Browser class is implementation of WebDriver to execute basic actions using it
 */
public class Browser implements WebDriver {
    private final WebDriver driver;
    private final JsExecutor jsExecutor;
    private final ScreenShooter shooter;
    private FluentWait<WebDriver> wait;

    private static final long TIMEOUT_SECONDS = 30;

    public Browser(String browserType) {
        this.driver = initializeWebDriver(browserType.toLowerCase());
        this.jsExecutor = new JsExecutor(driver);
        this.shooter = new ScreenShooter(driver);
        this.setDefaultWaitTimeout();
    }

    public JsExecutor getJsExecutor() {
        return this.jsExecutor;
    }

    public ScreenShooter getScreenShooter() {
        return this.shooter;
    }

    @Override
    public WebElement findElement(By by) {
        return getWebDriver().findElement(by);
    }

    @Override
    public String getPageSource() {
        return getWebDriver().getPageSource();
    }

    @Override
    public List<WebElement> findElements(By by) {
        return getWebDriver().findElements(by);
    }

    @Override
    public void get(String url) {
        getWebDriver().get(url);
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

    @Override
    public Navigation navigate() {
        return getWebDriver().navigate();
    }

    @Override
    public Options manage() {
        return getWebDriver().manage();
    }

    public Element getElement(By by) {
        return new ElementImpl(by, getWebDriver());
    }

    public Element getElement(LazyLocator locator) {
        return new ElementImpl(locator);
    }

    public Elements<Element> getElements(By by) {
        return new ElementsImpl<>(by, getWebDriver(), Element.class);
    }

    public Elements<Element> getElements(LazyLocator locator) {
        return new ElementsImpl<>(locator, Element.class);
    }

    public WebDriver getWebDriver() {
        return driver;
    }

    public FluentWait<WebDriver> getWait() {
        return wait;
    }

    public FluentWait<WebDriver> getWait(long seconds) {
        setWaitTimeout(seconds);
        return wait;
    }

    public void setDefaultWaitTimeout() {
        this.wait = new WebDriverWait(this.driver, TIMEOUT_SECONDS);
    }

    public void setWaitTimeout(long seconds) {
        this.wait = new WebDriverWait(getWebDriver(), seconds);
    }

    public void takeScreenShot(String fileName) {
        shooter.getScreenShot(fileName);
    }

    public void waitForPageToLoad() {
        try {
            getWait().until(WaitConditions.pageLoaded(getWebDriver()));
        } catch (TimeoutException | NullPointerException e) {
            //sometimes PageInfo remains in Interactive mode and never becomes Complete, then we can still try to access the controls
            String state = getJsExecutor().getReadyState();
            if (!state.equalsIgnoreCase("interactive")) throw e;
        } catch (WebDriverException e) {
            String state = getJsExecutor().getReadyState();
            if (!(state.equalsIgnoreCase("complete") || state.equalsIgnoreCase("loaded"))) throw e;
        }
    }

    public boolean elementExists(By locator) {
        try {
            getWebDriver().findElement(locator);
        } catch (NoSuchElementException e) {
            return false;
        }
        return true;
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
                        System.setProperty("webdriver.chrome.driver", getDriversDirPath()+"chromedriver.exe");
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
            driver.manage().window().maximize();
            driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
            return driver;
        } catch (IllegalArgumentException ex) {
            return null;
        }
    }

    private String getDriversDirPath() {
        return System.getProperty("user.dir")+"\\src\\resources\\drivers\\";
    }
}