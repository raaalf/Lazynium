package com.malski.core.mobile.control;

import com.malski.core.mobile.factory.LazyLocator;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

import static com.malski.core.mobile.conditions.WaitConditions.*;
import static com.malski.core.mobile.conditions.WaitConditions.elementToBeClickable;
import static com.malski.core.mobile.conditions.WaitConditions.invisibilityOfElementLocated;
import static com.malski.core.mobile.conditions.WaitConditions.presenceOfElementLocated;
import static com.malski.core.mobile.conditions.WaitConditions.visibilityOfElementLocated;
import static com.malski.core.utils.TestContext.getConfig;
import static org.openqa.selenium.support.ui.ExpectedConditions.*;
import static org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable;
import static org.openqa.selenium.support.ui.ExpectedConditions.invisibilityOfElementLocated;
import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated;
import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;

public class Application extends LazyMobileContext {
    private FluentWait<WebDriver> wait;
    private LazyMobileDriver driver;
    private Actions actions;


    public Application(String osType) {
        driver = new LazyMobileDriver(osType);
    }

    public String getOsType() {
        return getDriver().getOs();
    }

    public LazyMobileDriver getDriver() {
        return driver;
    }

    @SuppressWarnings("unchecked")
    @Override
    public LazyMobileDriver getSearchContext() {
        return getDriver();
    }

    @Override
    public boolean refresh() {
        return false;
    }

    public void waitForScreenToLoad() {
        // TODO
    }

    public Actions getActions() {
        if(this.actions == null) {
            this.actions = new Actions(getWrappedDriver());
        }
        return this.actions;
    }

    public void setImplicitlyWait(long time, TimeUnit timeUnit) {
        getWrappedDriver().manage().timeouts().implicitlyWait(time, timeUnit);
    }

    public FluentWait<WebDriver> getWait() {
        return new WebDriverWait(getWrappedDriver(), getConfig().getExplicitlyTimeout(), getConfig().getDriverSleepMs());
    }

    public FluentWait<WebDriver> getWait(long seconds) {
        return new WebDriverWait(getWrappedDriver(), seconds, getConfig().getDriverSleepMs());
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
        return getWrappedDriver().switchTo().alert();
    }

    public boolean isAlertPresent() {
        try {
            waitUntilAlertIsPresent(1);
            return true;
        } catch (TimeoutException eTO) {
            return false;
        }
    }
}
