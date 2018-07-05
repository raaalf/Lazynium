package com.malski.lazynium.web.factory;

import com.malski.lazynium.utils.PropertyKey;
import io.github.bonigarcia.wdm.*;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.LocalFileDetector;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import static com.malski.lazynium.utils.TestContext.config;
import static com.malski.lazynium.utils.TestContext.getPropertyByKey;

public class BrowserFactory {

    public static WebDriver initializeDriver(String browser) {
        WebDriver driver;
        if (config().grid()) {
            driver = initializeWebDriverForGrid(browser);
        } else {
            driver = initializeWebDriver(browser);
        }
        setBrowserSize(driver);
        driver.manage().timeouts().implicitlyWait(config().implicitlyTimeoutMs(), TimeUnit.MILLISECONDS);
        return driver;
    }

    private static WebDriver initializeWebDriver(String browser) {
        WebDriver driver;
        switch (browser) {
            case "chrome":
            case "ch":
                ChromeDriverManager.getInstance().setup();
                driver = new ChromeDriver(CapabilitiesFactory.getChromeCapabilities());
                break;
            case "firefox":
            case "ff":
                FirefoxDriverManager.getInstance().setup();
                driver = new FirefoxDriver(CapabilitiesFactory.getFirefoxCapabilities(false));
                break;
            case "gecko":
                FirefoxDriverManager.getInstance().setup();
                driver = new FirefoxDriver(CapabilitiesFactory.getFirefoxCapabilities(true));
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
            case "phantom_js":
                PhantomJsDriverManager.getInstance().setup();
                driver = new PhantomJSDriver();
                break;
            default:
                driver = new HtmlUnitDriver();
        }
        new MultiDisplayHandler().moveBrowserToDisplay(driver);
        return driver;
    }

    private static WebDriver initializeWebDriverForGrid(String browser) {
        DesiredCapabilities cap;
        URL hubUrl;
        try {
            hubUrl = new URL(getPropertyByKey(PropertyKey.GRID_HOST) + "/wd/hub");
        } catch (MalformedURLException e) {
            throw new RuntimeException("Couldn't establish grid hub url!", e);
        }
        switch (browser) {
            case "chrome":
            case "ch":
                cap = CapabilitiesFactory.getChromeCapabilities();
                break;
            case "firefox":
            case "ff":
                cap = CapabilitiesFactory.getFirefoxCapabilities(false);
                break;
            case "gecko":
                cap = CapabilitiesFactory.getFirefoxCapabilities(true);
                break;
            case "edge":
                cap = DesiredCapabilities.edge();
                break;
            case "internet_explorer":
            case "ie":
                cap = DesiredCapabilities.internetExplorer();
                break;
            case "phantomjs":
                PhantomJsDriverManager.getInstance().setup();
                cap = DesiredCapabilities.phantomjs();
                break;
            default:
                throw new RuntimeException("Grid driver not implemented for: " + browser);
        }
        WebDriver driver = new RemoteWebDriver(hubUrl, cap);
        ((RemoteWebDriver) driver).setFileDetector(new LocalFileDetector());
        return driver;
    }

    private static void setBrowserSize(WebDriver driver) {
        String resolution = getPropertyByKey(PropertyKey.RESOLUTION);
        String[] resolutions = StringUtils.isBlank(resolution) ? new String [0] : resolution.split("x");
        if (resolutions.length > 1) {
            try {
                Dimension dim = new Dimension(Integer.parseInt(resolutions[0]), Integer.parseInt(resolutions[1]));
                driver.manage().window().setSize(dim);
            } catch(NumberFormatException ignore) {
                driver.manage().window().maximize();
            }
        } else {
            driver.manage().window().maximize();
        }
    }
}