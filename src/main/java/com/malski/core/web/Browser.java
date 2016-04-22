package com.malski.core.web;

import com.malski.core.web.elements.Element;
import com.malski.core.web.elements.ElementImpl;
import com.malski.core.web.elements.ElementsList;
import com.malski.core.web.elements.ElementsListImpl;
import io.github.bonigarcia.wdm.ChromeDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Browser class is implementation of WebDriver to execute basic actions using it
 */
public class Browser implements WebDriver {
    private WebDriver driver;
    private String baseUrl;
    private Wait<WebDriver> wait;

    private static final long TIMEOUT_SECONDS = 60;

    public Browser(String browserType, String baseUrl) {
        this.driver = initializeWebDriver(browserType.toLowerCase());
        this.baseUrl = baseUrl;
        this.setDefaultWaitTimeout();
        get(baseUrl);
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

    public ElementsList<Element> getElements(By by) {
        return new ElementsListImpl<>(by, getWebDriver(), Element.class);
    }

    public String getUrl() {
        return baseUrl;
    }

    public WebDriver getWebDriver() {
        return driver;
    }

    public FluentWait<WebDriver> getWait() {
        return getWait(TIMEOUT_SECONDS);
    }

    public FluentWait<WebDriver> getWait(long timeout) {
        return new WebDriverWait(getWebDriver(), timeout);
    }

    public void setDefaultWaitTimeout() {
        this.wait = new WebDriverWait(this.driver, 30L);
    }

    public void setWaitTimeout(long milliseconds) {
        this.wait = new WebDriverWait(getWebDriver(), milliseconds / 1000L);
    }

    public Object executeScript(String script, Object... objects) {
        return ((JavascriptExecutor) getWebDriver()).executeScript(script, objects);
    }

    public void scrollIntoView(WebElement element) {
        executeScript("arguments[0].scrollIntoView(true);", element);
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
                    ChromeDriverManager.getInstance().setup();
                    driver = new ChromeDriver();
                    break;
                case "firefox":
                case "ff":
                    driver = new FirefoxDriver();
                    break;
                case "edge":
                    driver = new EdgeDriver();
                    break;
                case "internet_explorer":
                case "ie":
                    driver = new InternetExplorerDriver();
                    break;
                default:
                    driver = new PhantomJSDriver();
            }
            driver.manage().window().maximize();
            driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
            return driver;
        } catch (IllegalArgumentException ex) {
            return null;
        }
    }
}