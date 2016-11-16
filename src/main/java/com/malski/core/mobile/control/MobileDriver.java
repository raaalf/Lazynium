package com.malski.core.mobile.control;

import com.google.gson.JsonObject;
import io.appium.java_client.MobileElement;
import io.appium.java_client.MultiTouchAction;
import io.appium.java_client.TouchAction;
import org.openqa.selenium.*;
import org.openqa.selenium.html5.Location;
import org.openqa.selenium.remote.ExecuteMethod;
import org.openqa.selenium.remote.Response;

import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface MobileDriver extends JavascriptExecutor, LazySearchContext {
    void get(String var1);

    String getCurrentUrl();

    String getTitle();

    String getPageSource();

    void close();

    void quit();

    Set<String> getWindowHandles();

    String getWindowHandle();

    WebDriver.TargetLocator switchTo();

    WebDriver.Navigation navigate();

    WebDriver.Options manage();

    void rotate(DeviceRotation deviceRotation);

    DeviceRotation rotation();

    Response execute(String driverCommand, Map<String, ?> parameters);

    ExecuteMethod getExecuteMethod();

    void resetApp();

    boolean isAppInstalled(String bundleId);

    void installApp(String appPath);

    void removeApp(String bundleId);

    void launchApp();

    void closeApp();

    void runAppInBackground(int seconds);

    String getDeviceTime();

    void hideKeyboard();

    byte[] pullFile(String remotePath);

    byte[] pullFolder(String remotePath);

    TouchAction performTouchAction(TouchAction touchAction);

    void performMultiTouchAction(MultiTouchAction multiAction);

    void tap(int fingers, WebElement element, int duration);

    void tap(int fingers, int x, int y, int duration);

    void swipe(int var1, int var2, int var3, int var4, int var5);

    void pinch(WebElement el);

    void pinch(int x, int y);

    void zoom(WebElement el);

    void zoom(int x, int y);

    JsonObject getSettings();

    WebDriver context(String name);

    Set<String> getContextHandles();

    String getContext();

    void rotate(ScreenOrientation orientation);

    ScreenOrientation getOrientation();

    Location location();

    void setLocation(Location location);

    Map<String, String> getAppStringMap();

    Map<String, String> getAppStringMap(String language);

    Map<String, String> getAppStringMap(String language, String stringFile);

    URL getRemoteAddress();

    Map<String, Object> getSessionDetails();

    void hideKeyboard(String strategy, String keyName);

    void hideKeyboard(String keyName);

    void shake();

    MobileElement findElementByIosUIAutomation(String using) throws WebDriverException;

    List<MobileElement> findElementsByIosUIAutomation(String using) throws WebDriverException;

    void lockDevice(int seconds);
}
