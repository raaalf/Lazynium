package com.malski.core.web.control;

import com.machinepublishers.jbrowserdriver.JBrowserDriver;
import com.malski.core.utils.PropertyKey;
import com.malski.core.web.conditions.WaitConditions;
import com.malski.core.web.factory.LazyLocator;
import io.github.bonigarcia.wdm.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.Point;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.internal.BuildInfo;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.awt.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.malski.core.utils.TestContext.getConfig;
import static com.malski.core.utils.TestContext.getPropertyByKey;
import static com.malski.core.web.conditions.WaitConditions.*;
import static com.malski.core.web.conditions.WaitConditions.elementToBeClickable;
import static com.malski.core.web.conditions.WaitConditions.invisibilityOfElementLocated;
import static com.malski.core.web.conditions.WaitConditions.presenceOfElementLocated;
import static com.malski.core.web.conditions.WaitConditions.visibilityOfElementLocated;
import static org.openqa.selenium.support.ui.ExpectedConditions.*;
import static org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable;
import static org.openqa.selenium.support.ui.ExpectedConditions.invisibilityOfElementLocated;
import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated;
import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;


/**
 * Browser class is implementation of WebDriver to execute basic actions using it
 */
public class Browser extends LazySearchContext implements WebDriver {
    private WebDriver driver;
    private JsExecutor jsExecutor;
    private ScreenShooter shooter;
    private Actions actions;
    private String browserType;
    private String latestWindowHandle;

    protected final Logger log = Logger.getLogger(getClass());

    public Browser(String browserType) {
        this.browserType = browserType.toLowerCase();
        initialize();
    }

    public JsExecutor getJsExecutor() {
        if(this.jsExecutor == null) {
            this.jsExecutor = new JsExecutor(getWebDriver());
        }
        return this.jsExecutor;
    }

    public ScreenShooter getScreenShooter() {
        if(this.shooter == null) {
            this.shooter = new ScreenShooter(getWebDriver());
        }
        return this.shooter;
    }

    public Actions getActions() {
        if(this.actions == null) {
            this.actions = new Actions(getWebDriver());
        }
        return this.actions;
    }

    public String getBrowserType() {
        switch (browserType) {
            case "chrome":
            case "ch":
                return "chrome";
            case "firefox":
            case "ff":
                return "firefox";
            case "edge":
                return "edge";
            case "internet_explorer":
            case "ie":
                return "ie";
            case "marionette":
                return "marionette";
            case "phantomjs":
                return "phantomjs";
            case "jbrowser":
                return "jbrowser";
            default:
                return "htmlunit";
        }
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
        initialize(initializeWebDriver(this.browserType));
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

    public void setImplicitlyWait(long time, TimeUnit timeUnit) {
        getWebDriver().manage().timeouts().implicitlyWait(time, timeUnit);
    }

    public FluentWait<WebDriver> getWait() {
        return new WebDriverWait(getWebDriver(), getConfig().getExplicitlyTimeout(), getConfig().getDriverSleepMs());
    }

    public FluentWait<WebDriver> getWait(long seconds) {
        return new WebDriverWait(getWebDriver(), seconds, getConfig().getDriverSleepMs());
    }

    public void waitForPageToLoad() {
        waitForPageToLoad(getConfig().getMaxTimeout());
    }

    public void waitForPageToLoad(long timePageLoad) {
        try {
            if(isAlertPresent()) {
                return; // alert opened so page is loaded
            }
            getWait(timePageLoad).until(WaitConditions.pageLoaded());
        } catch (TimeoutException | NullPointerException e) {
            String state = getJsExecutor().getReadyState();
            if (!state.equalsIgnoreCase("interactive")) {
                throw e;
            }
        } catch (WebDriverException e) {
            String state = getJsExecutor().getReadyState();
            if (!(state.equalsIgnoreCase("complete") || state.equalsIgnoreCase("loaded"))) {
                throw e;
            }
        }
    }

    public void waitUntilPresent(By by) {
        getWait().until(presenceOfElementLocated(by));
    }

    public void waitUntilPresent(LazyLocator locator) {
        getWait().until(presenceOfElementLocated(locator));
    }

    public void waitUntilPresent(By by, long timeout) {
        getWait(timeout).until(presenceOfElementLocated(by));
    }

    public void waitUntilPresent(LazyLocator locator, long timeout) {
        getWait(timeout).until(presenceOfElementLocated(locator));
    }

    public void waitUntilVisible(By by) {
        getWait().until(visibilityOfElementLocated(by));
    }

    public void waitUntilVisible(LazyLocator locator) {
        getWait().until(visibilityOfElementLocated(locator));
    }

    public void waitUntilVisible(By by, long timeout) {
        getWait(timeout).until(visibilityOfElementLocated(by));
    }

    public void waitUntilVisible(LazyLocator locator, long timeout) {
        getWait(timeout).until(visibilityOfElementLocated(locator));
    }

    public void waitUntilDisappear(By by) {
        getWait().until(invisibilityOfElementLocated(by));
    }

    public void waitUntilDisappear(LazyLocator locator) {
        getWait().until(invisibilityOfElementLocated(locator));
    }

    public void waitUntilDisappear(By by, long timeout) {
        getWait(timeout).until(invisibilityOfElementLocated(by));
    }

    public void waitUntilDisappear(LazyLocator locator, long timeout) {
        getWait(timeout).until(invisibilityOfElementLocated(locator));
    }

    public void waitUntilEnabled(By by) {
        getWait().until(elementToBeClickable(by));
    }

    public void waitUntilEnabled(LazyLocator locator) {
        getWait().until(elementToBeClickable(locator));
    }

    public void waitUntilEnabled(By by, long timeout) {
        getWait(timeout).until(elementToBeClickable(by));
    }

    public void waitUntilEnabled(LazyLocator locator, long timeout) {
        getWait(timeout).until(elementToBeClickable(locator));
    }

    public void waitUntilDisabled(By by) {
        getWait().until(not(elementToBeClickable(by)));
    }

    public void waitUntilDisabled(LazyLocator locator) {
        getWait().until(not(elementToBeClickable(locator)));
    }

    public void waitUntilDisabled(By by, long timeout) {
        getWait(timeout).until(not(elementToBeClickable(by)));
    }

    public void waitUntilDisabled(LazyLocator locator, long timeout) {
        getWait(timeout).until(not(elementToBeClickable(locator)));
    }

    public void waitUntilAttributeChange(By by, String attributeName, String expectedValue) {
        getWait().until(attributeChanged(by, attributeName, expectedValue));
    }

    public void waitUntilAttributeChange(LazyLocator locator, String attributeName, String expectedValue) {
        getWait().until(attributeChanged(locator, attributeName, expectedValue));
    }

    public void waitUntilAttributeChange(By by, String attributeName, String expectedValue, long timeout) {
        getWait(timeout).until(attributeChanged(by, attributeName, expectedValue));
    }

    public void waitUntilAttributeChange(LazyLocator locator, String attributeName, String expectedValue, long timeout) {
        getWait(timeout).until(attributeChanged(locator, attributeName, expectedValue));
    }

    public void waitUntilAttributeFrom(LazyLocator locator, String attributeName, String fromValue) {
        getWait().until(attributeChangedFrom(locator, attributeName, fromValue));
    }

    public void waitUntilIsInViewport(By by) {
        getWait().until(isInViewPort(by));
    }

    public void waitUntilIsInViewport(LazyLocator locator) {
        getWait().until(isInViewPort(locator));
    }

    public void waitUntilIsInViewport(By by, long timeout) {
        getWait(timeout).until(isInViewPort(by));
    }

    public void waitUntilIsInViewport(LazyLocator locator, long timeout) {
        getWait(timeout).until(isInViewPort(locator));
    }

    public void waitUntilAlertIsPresent() {
        getWait().until(alertIsPresent());
    }

    public void waitUntilAlertIsPresent(long timeout) {
        getWait(timeout).until(alertIsPresent());
    }

    public void waitUntilIsClickable(LazyLocator locator) {
        waitUntilEnabled(locator);
    }

    public void waitUntilIsClickable(LazyLocator locator, long timeout) {
        waitUntilEnabled(locator, timeout);
    }

    public void waitUntilNewWindowOpened(int beforeWindowsCount) {
        getWait().until(newWindowOpened(beforeWindowsCount));
    }

    public void waitUntilCurrentWindowClosed(int beforeWindowsCount) {
        getWait().until(currentWindowClosed(beforeWindowsCount));
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
        return ((RemoteWebDriver) getWebDriver()).getCapabilities();
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
        // This block to find out IE Version number
        if ("internet explorer".equalsIgnoreCase(cap.getBrowserName())) {
            String uAgent = getJsExecutor().executeScript("return navigator.userAgent;");
            System.out.println(uAgent);
            //uAgent return as "MSIE 8.0 Windows" for IE8
            if (uAgent.contains("MSIE") && uAgent.contains("Windows")) {
                version = uAgent.substring(uAgent.indexOf("MSIE") + 5, uAgent.indexOf("Windows") - 2);
            } else if (uAgent.contains("Trident/7.0")) {
                version = "11.0";
            } else {
                version = "0.0";
            }
        } else {
            //Browser version for Firefox and Chrome
            version = cap.getVersion();
        }
        return version;
    }

    private WebDriver initializeWebDriver(String browser) {
        try {
            WebDriver driver;
            switch (browser) {
                case "chrome":
                case "ch":
                    ChromeDriverManager.getInstance().setup();
                    driver = new ChromeDriver(getChromeDesiredCapabilities());
                    break;
                case "firefox":
                case "ff":
                    driver = new FirefoxDriver(getFirefoxBinary(), getFirefoxProfile());
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
                case "gecko":
                    MarionetteDriverManager.getInstance().setup();
                    DesiredCapabilities capabilities = DesiredCapabilities.firefox();
                    capabilities.setCapability("marionette", true);
                    driver = new FirefoxDriver(capabilities);
                    break;
                case "jbrowser":
                    driver = new JBrowserDriver();
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

    private DesiredCapabilities addProxyCapabilities(DesiredCapabilities capability) {
        String proxyAddress = getPropertyByKey(PropertyKey.PROXY_URL);
        return addProxyCapabilities(capability, proxyAddress, proxyAddress, proxyAddress);
    }

    private DesiredCapabilities addProxyCapabilities(DesiredCapabilities capability, String httpProxy, String sslProxy,
                                                     String ftpProxy) {
        Proxy proxy = new Proxy();
        proxy.setProxyType(Proxy.ProxyType.MANUAL);
        proxy.setHttpProxy(httpProxy);
        proxy.setSslProxy(sslProxy);
        proxy.setFtpProxy(ftpProxy);
        capability.setCapability(CapabilityType.PROXY, proxy);
        capability.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
        return capability;
    }

    private DesiredCapabilities getChromeDesiredCapabilities() {
        DesiredCapabilities caps = DesiredCapabilities.chrome();
        ChromeOptions options = new ChromeOptions();
        Map<String, Object> chromePrefs = new HashMap<>();
        chromePrefs.put("profile.default_content_settings.geolocation", 2);
        chromePrefs.put("download.prompt_for_download", false);
        chromePrefs.put("download.directory_upgrade", true);
        chromePrefs.put("download.default_directory", getConfig().getDownloadDirPath());
        chromePrefs.put("password_manager_enabled", false);
        chromePrefs.put("safebrowsing.enabled", "true");
        options.setExperimentalOption("prefs", chromePrefs);
        caps.setCapability(ChromeOptions.CAPABILITY, options);
        boolean proxyEnabled = Boolean.valueOf(getPropertyByKey(PropertyKey.PROXY));
        if (proxyEnabled) {
            caps = addProxyCapabilities(caps);
        }
        return caps;
    }

    private FirefoxProfile addProxyProfile(FirefoxProfile ffProfile) {
        URL proxyAddress;
        try {
            proxyAddress = new URL(getPropertyByKey(PropertyKey.PROXY_URL));
        } catch (MalformedURLException e) {
            throw new RuntimeException("Couldn't set proxy for firefox", e);
        }
        ffProfile.setPreference("network.proxy.type", 1);
        ffProfile.setPreference("network.proxy.http", proxyAddress.getHost());
        ffProfile.setPreference("network.proxy.http_port", proxyAddress.getPort());
        ffProfile.setPreference("network.proxy.ssl", proxyAddress.getHost());
        ffProfile.setPreference("network.proxy.ssl_port", proxyAddress.getPort());
        return ffProfile;
    }

    private FirefoxBinary getFirefoxBinary() {
        FirefoxBinary ffBinary = new FirefoxBinary();
        ffBinary.setTimeout(TimeUnit.SECONDS.toMillis(180));
        return ffBinary;
    }

    private FirefoxProfile getFirefoxProfile() {
        FirefoxProfile ffProfile = new FirefoxProfile();
        ffProfile.setPreference("browser.download.folderList", 2);
        ffProfile.setPreference("browser.download.manager.showWhenStarting", false);
        ffProfile.setPreference("browser.download.dir", getConfig().getDownloadDirPath());
        ffProfile.setPreference("browser.helperApps.neverAsk.saveToDisk", "application/zip;application/octet-stream;application/x-zip;application/x-zip-compressed;text/css;text/html;text/plain;text/xml;text/comma-separated-values");
        ffProfile.setPreference("browser.helperApps.neverAsk.openFile", "application/zip;application/octet-stream;application/x-zip;application/x-zip-compressed;text/css;text/html;text/plain;text/xml;text/comma-separated-values");
        ffProfile.setPreference("browser.helperApps.alwaysAsk.force", false);
        boolean proxyEnabled = Boolean.valueOf(getPropertyByKey(PropertyKey.PROXY));
        if (proxyEnabled) {
            ffProfile = addProxyProfile(ffProfile);
        }
        return ffProfile;
    }

    private void moveBrowserToAnotherScreen(WebDriver driver) {
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

    private int getNumberOfScreens() {
        try {
            GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
            GraphicsDevice[] devices = env.getScreenDevices();
            return devices.length;
        } catch (HeadlessException e) {
            return 1;
        }
    }
}