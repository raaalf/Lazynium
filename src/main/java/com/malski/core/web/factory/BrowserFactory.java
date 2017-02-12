package com.malski.core.web.factory;

import com.malski.core.utils.PropertyKey;
import io.github.bonigarcia.wdm.*;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.awt.*;
import java.util.concurrent.TimeUnit;

import static com.malski.core.utils.TestContext.getConfig;
import static com.malski.core.utils.TestContext.getPropertyByKey;

public class BrowserFactory {

    public static WebDriver initializeWebDriver(String browser) {
        try {
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
            moveBrowserToAnotherScreen(driver);
            driver.manage().window().maximize();
            driver.manage().timeouts().implicitlyWait(getConfig().getImplicitlyTimeoutMs(), TimeUnit.MILLISECONDS);
            return driver;
        } catch (IllegalArgumentException ex) {
            return null;
        }
    }

    private static void moveBrowserToAnotherScreen(WebDriver driver) {
        String multiScreen = getPropertyByKey(PropertyKey.DISPLAY_DIRECTION);
        if (!StringUtils.isBlank(multiScreen) && getNumberOfScreens() > 1) {
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

    private static int getNumberOfScreens() {
        try {
            GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
            GraphicsDevice[] devices = env.getScreenDevices();
            return devices.length;
        } catch (HeadlessException e) {
            return 1;
        }
    }
}