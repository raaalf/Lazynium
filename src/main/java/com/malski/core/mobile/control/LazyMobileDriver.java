package com.malski.core.mobile.control;

import com.google.gson.JsonObject;
import com.malski.core.utils.TestContext;
import io.appium.java_client.*;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.ios.IOSMobileCommandHelper;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.*;
import org.openqa.selenium.html5.Location;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.ExecuteMethod;
import org.openqa.selenium.remote.Response;
import sun.security.util.PendingException;

import java.io.File;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import static com.malski.core.utils.TestContext.getConfig;

public class LazyMobileDriver extends LazyMobileContext implements MobileDriver {
    private AppiumDriver<? extends MobileElement> driver;
    private String osType;

    public LazyMobileDriver(String osType) {
        this.osType = osType.toLowerCase();
        initializeDriver(this.osType);
    }

    public AppiumDriver<? extends MobileElement> getWrappedDriver() {
        return driver;
    }

    public String getOs() {
        switch (osType) {
            case "ios":
                return "ios";
            case "android":
                return "android";
            default:
                return "mobile";
        }
    }

    @Override
    public <T extends MobileElement> T getSearchContext() {
        return null;
    }

    @Override
    public boolean refresh() {
        if (getWrappedDriver().toString().contains("null")) {
            initializeDriver(this.osType);
            return true;
        }
        return false;
    }

    @SuppressWarnings("unchecked")
    public MobileElement findElementByOsAutomation(String using) throws WebDriverException {
        switch (getOs()) {
            case "ios":
                return ((IOSDriver<IOSElement>) getWrappedDriver()).findElementByIosUIAutomation(using);
            case "android":
                return ((AndroidDriver<AndroidElement>) getWrappedDriver()).findElementByAndroidUIAutomator(using);
            default:
                throw new RuntimeException("Not implemented ui specific find for type " + getSearchContext().getClass());
        }
    }

    @SuppressWarnings("unchecked")
    public List<MobileElement> findElementsByOsAutomation(String using) throws WebDriverException {
        switch (getOs()) {
            case "ios":
                return ((IOSDriver) getWrappedDriver()).findElementsByIosUIAutomation(using);
            case "android":
                return ((AndroidDriver) getWrappedDriver()).findElementsByAndroidUIAutomator(using);
            default:
                throw new RuntimeException("Not implemented ui specific find for type " + getSearchContext().getClass());
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<MobileElement> findElements(By by) {
        return (List<MobileElement>) getWrappedDriver().findElements(by);
    }

    @Override
    public MobileElement findElement(By by) {
        return getWrappedDriver().findElement(by);
    }

    @Override
    public MobileElement findElement(String by, String using) {
        return getWrappedDriver().findElement(by, using);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<MobileElement> findElements(String by, String using) {
        return (List<MobileElement>) getWrappedDriver().findElements(by, using);
    }

    @Override
    public MobileElement findElementByAccessibilityId(String using) {
        return getSearchContext().findElementByAccessibilityId(using);
    }

    @Override
    public List<MobileElement> findElementsByAccessibilityId(String using) {
        return getSearchContext().findElementsByAccessibilityId(using);
    }

    // web part

    public String getPageSource() {
        return getWrappedDriver().getPageSource();
    }

    public void get(String url) {
        getWrappedDriver().get(url);
    }

    public void navigateTo(String url) {
        getWrappedDriver().get(url);
    }

    public void switchToNewWindow() {
        for (String winHandle : getWindowHandles()) {
            switchTo().window(winHandle);
        }
    }

    public void switchToNextWindow() {
        List<String> handles = Arrays.asList(getWindowHandles().toArray(new String[0]));
        int indexOfCurrentPage = handles.indexOf(getWindowHandle());
        if (indexOfCurrentPage < handles.size() - 1)
            switchTo().window(handles.get(indexOfCurrentPage + 1));
    }

    public void switchToPreviousWindow() {
        List<String> handles = Arrays.asList(getWindowHandles().toArray(new String[0]));
        int indexOfCurrentPage = handles.indexOf(getWindowHandle());
        if (indexOfCurrentPage > 0)
            switchTo().window(handles.get(indexOfCurrentPage - 1));
    }

    public WebDriver.Navigation navigate() {
        return getWrappedDriver().navigate();
    }

    public WebDriver.Options manage() {
        return getWrappedDriver().manage();
    }

    public String getCurrentUrl() {
        return getWrappedDriver().getCurrentUrl();
    }

    public String getTitle() {
        return getWrappedDriver().getTitle();
    }

    public void close() {
        getWrappedDriver().close();
    }

    public void quit() {
        getWrappedDriver().quit();
    }

    public Set<String> getWindowHandles() {
        return getWrappedDriver().getWindowHandles();
    }

    public String getWindowHandle() {
        return getWrappedDriver().getWindowHandle();
    }

    public WebDriver.TargetLocator switchTo() {
        return getWrappedDriver().switchTo();
    }

    // mobile part

    public void rotate(DeviceRotation deviceRotation) {
        getWrappedDriver().rotate(deviceRotation);
    }

    public DeviceRotation rotation() {
        return getWrappedDriver().rotation();
    }

    public Response execute(String driverCommand, Map<String, ?> parameters) {
        return getWrappedDriver().execute(driverCommand, parameters);
    }

    public ExecuteMethod getExecuteMethod() {
        return getWrappedDriver().getExecuteMethod();
    }

    public void resetApp() {
        getWrappedDriver().resetApp();
    }

    public boolean isAppInstalled(String bundleId) {
        return getWrappedDriver().isAppInstalled(bundleId);
    }

    public void installApp(String appPath) {
        getWrappedDriver().installApp(appPath);
    }

    public void removeApp(String bundleId) {
        getWrappedDriver().removeApp(bundleId);
    }

    public void launchApp() {
        getWrappedDriver().launchApp();
    }

    public void closeApp() {
        getWrappedDriver().close();
    }

    public void runAppInBackground(int seconds) {
        getWrappedDriver().runAppInBackground(seconds);
    }

    public String getDeviceTime() {
        return getWrappedDriver().getDeviceTime();
    }

    public void hideKeyboard() {
        getWrappedDriver().hideKeyboard();
    }

    public byte[] pullFile(String remotePath) {
        return getWrappedDriver().pullFile(remotePath);
    }

    public byte[] pullFolder(String remotePath) {
        return getWrappedDriver().pullFolder(remotePath);
    }

    public TouchAction performTouchAction(TouchAction touchAction) {
        return getWrappedDriver().performTouchAction(touchAction);
    }

    public void performMultiTouchAction(MultiTouchAction multiAction) {
        getWrappedDriver().performMultiTouchAction(multiAction);
    }

    public void tap(int fingers, WebElement element, int duration) {
        getWrappedDriver().tap(fingers, element, duration);
    }

    public void tap(int fingers, int x, int y, int duration) {
        getWrappedDriver().tap(fingers, x, y, duration);
    }

    public void swipe(int var1, int var2, int var3, int var4, int var5) {
        getWrappedDriver().swipe(var1, var3, var3, var4, var5);
    }

    public void pinch(WebElement el) {
        getWrappedDriver().pinch(el);
    }

    public void pinch(int x, int y) {
        getWrappedDriver().pinch(x, y);
    }

    public void zoom(WebElement el) {
        getWrappedDriver().zoom(el);
    }

    public void zoom(int x, int y) {
        getWrappedDriver().zoom(x, y);
    }

    public JsonObject getSettings() {
        return getWrappedDriver().getSettings();
    }

    public WebDriver context(String name) {
        return getWrappedDriver().context(name);
    }

    public Set<String> getContextHandles() {
        return getWrappedDriver().getContextHandles();
    }

    public String getContext() {
        return getWrappedDriver().getContext();
    }

    public void rotate(ScreenOrientation orientation) {
        getWrappedDriver().rotate(orientation);
    }

    public ScreenOrientation getOrientation() {
        return getWrappedDriver().getOrientation();
    }

    public Location location() {
        return getWrappedDriver().location();
    }

    public void setLocation(Location location) {
        getWrappedDriver().setLocation(location);
    }

    public Map<String, String> getAppStringMap() {
        return getWrappedDriver().getAppStringMap();
    }

    public Map<String, String> getAppStringMap(String language) {
        return getWrappedDriver().getAppStringMap(language);
    }

    public Map<String, String> getAppStringMap(String language, String stringFile) {
        return getWrappedDriver().getAppStringMap(language, stringFile);
    }

    public URL getRemoteAddress() {
        return getWrappedDriver().getRemoteAddress();
    }

    public Map<String, Object> getSessionDetails() {
        return getWrappedDriver().getSessionDetails();
    }

    public void hideKeyboard(String strategy, String keyName) {
        switch (getOs()) {
            case "ios":
                ((IOSDriver) getWrappedDriver()).hideKeyboard(strategy, keyName);
//            case "android":
//                return ((AndroidDriver) getWrappedDriver()).hideKeyboard(strategy, keyName);
            default:
                throw new RuntimeException("Not implemented hideKeyboard with strategy and keyName " + getSearchContext().getClass());
        }
    }

    public void hideKeyboard(String keyName) {
        switch (getOs()) {
            case "ios":
                ((IOSDriver) getWrappedDriver()).hideKeyboard(keyName);
//            case "android":
//                return ((AndroidDriver) getWrappedDriver()).hideKeyboard(keyName);
            default:
                throw new RuntimeException("Not implemented hideKeyboard with strategy and keyName " + getSearchContext().getClass());
        }
    }

    public void shake() {
        CommandExecutionHelper.execute(this, IOSMobileCommandHelper.shakeCommand());
    }

    public MobileElement findElementByIosUIAutomation(String using) throws WebDriverException {
        return this.findElement("-ios uiautomation", using);
    }

    public List<MobileElement> findElementsByIosUIAutomation(String using) throws WebDriverException {
        return this.findElements("-ios uiautomation", using);
    }

    public void lockDevice(int seconds) {
        CommandExecutionHelper.execute(this, IOSMobileCommandHelper.lockDeviceCommand(seconds));
    }

    @SuppressWarnings("unchecked")
    private void initializeDriver(String osType) {
        try {
            switch (osType) {
                case "ios":
                    File app = new File(getConfig().getApp());
                    DesiredCapabilities capabilities = new DesiredCapabilities();
                    capabilities.setCapability(MobileCapabilityType.BROWSER_NAME, "");
                    capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, getConfig().getOsVersion());
                    capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, getConfig().getDevice());
                    capabilities.setCapability(MobileCapabilityType.APP, app.getAbsolutePath());
                    driver = new IOSDriver<IOSElement>(capabilities);
                    break;
                case "android":
                    app = new File(getConfig().getApp());
                    capabilities = new DesiredCapabilities();
                    capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, getConfig().getOsVersion());
                    capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, getConfig().getDevice());
                    capabilities.setCapability(MobileCapabilityType.APP, app.getAbsolutePath());
                    driver = new AndroidDriver<AndroidElement>(capabilities);
                    break;
                default:
                    throw new PendingException("OS not implemented: " + osType);
            }
            driver.manage().timeouts().implicitlyWait(TestContext.getConfig().getImplicitlyTimeoutMs(), TimeUnit.MILLISECONDS);
        } catch (IllegalArgumentException ex) {
            throw new RuntimeException("Problem during driver initialization.", ex);
        }
    }

    @Override
    public Object executeScript(String s, Object... objects) {
        return getWrappedDriver().executeScript(s, objects);
    }

    @Override
    public Object executeAsyncScript(String s, Object... objects) {
        return getWrappedDriver().executeAsyncScript(s, objects);
    }
}
