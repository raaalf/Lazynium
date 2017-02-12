package com.malski.core.web.control;

import com.malski.core.web.conditions.WaitConditions;
import com.malski.core.web.factory.LazyLocator;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;

import static com.malski.core.utils.TestContext.getConfig;
import static com.malski.core.web.conditions.WaitConditions.*;
import static com.malski.core.web.conditions.WaitConditions.elementToBeClickable;
import static org.openqa.selenium.support.ui.ExpectedConditions.*;
import static org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable;
import static org.openqa.selenium.support.ui.ExpectedConditions.invisibilityOfElementLocated;
import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated;
import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;

public interface BrowserWait {

    FluentWait<WebDriver> getWait();

    FluentWait<WebDriver> getWait(long seconds);

    default void waitUntilPageLoaded() {
        waitUntilPageLoaded(getConfig().getMaxTimeout());
    }

    void waitUntilPageLoaded(long timePageLoad);

    default void waitUntilPresent(By by) {
        getWait().until(presenceOfElementLocated(by));
    }

    default void waitUntilPresent(LazyLocator locator) {
        getWait().until(WaitConditions.presenceOfElementLocated(locator));
    }

    default void waitUntilPresent(By by, long timeout) {
        getWait(timeout).until(presenceOfElementLocated(by));
    }

    default void waitUntilPresent(LazyLocator locator, long timeout) {
        getWait(timeout).until(WaitConditions.presenceOfElementLocated(locator));
    }

    default void waitUntilVisible(By by) {
        getWait().until(visibilityOfElementLocated(by));
    }

    default void waitUntilVisible(LazyLocator locator) {
        getWait().until(WaitConditions.visibilityOfElementLocated(locator));
    }

    default void waitUntilVisible(By by, long timeout) {
        getWait(timeout).until(visibilityOfElementLocated(by));
    }

    default void waitUntilVisible(LazyLocator locator, long timeout) {
        getWait(timeout).until(WaitConditions.visibilityOfElementLocated(locator));
    }

    default void waitUntilDisappear(By by) {
        getWait().until(invisibilityOfElementLocated(by));
    }

    default void waitUntilDisappear(LazyLocator locator) {
        getWait().until(WaitConditions.invisibilityOfElementLocated(locator));
    }

    default void waitUntilDisappear(By by, long timeout) {
        getWait(timeout).until(invisibilityOfElementLocated(by));
    }

    default void waitUntilDisappear(LazyLocator locator, long timeout) {
        getWait(timeout).until(WaitConditions.invisibilityOfElementLocated(locator));
    }

    default void waitUntilEnabled(By by) {
        getWait().until(elementToBeClickable(by));
    }

    default void waitUntilEnabled(LazyLocator locator) {
        getWait().until(elementToBeClickable(locator));
    }

    default void waitUntilEnabled(By by, long timeout) {
        getWait(timeout).until(ExpectedConditions.elementToBeClickable(by));
    }

    default void waitUntilEnabled(LazyLocator locator, long timeout) {
        getWait(timeout).until(elementToBeClickable(locator));
    }

    default void waitUntilDisabled(By by) {
        getWait().until(not(ExpectedConditions.elementToBeClickable(by)));
    }

    default void waitUntilDisabled(LazyLocator locator) {
        getWait().until(not(elementToBeClickable(locator)));
    }

    default void waitUntilDisabled(By by, long timeout) {
        getWait(timeout).until(not(ExpectedConditions.elementToBeClickable(by)));
    }

    default void waitUntilDisabled(LazyLocator locator, long timeout) {
        getWait(timeout).until(not(elementToBeClickable(locator)));
    }

    default void waitUntilAttributeChange(By by, String attributeName, String expectedValue) {
        getWait().until(attributeChanged(by, attributeName, expectedValue));
    }

    default void waitUntilAttributeChange(LazyLocator locator, String attributeName, String expectedValue) {
        getWait().until(attributeChanged(locator, attributeName, expectedValue));
    }

    default void waitUntilAttributeChange(By by, String attributeName, String expectedValue, long timeout) {
        getWait(timeout).until(attributeChanged(by, attributeName, expectedValue));
    }

    default void waitUntilAttributeChange(LazyLocator locator, String attributeName, String expectedValue, long timeout) {
        getWait(timeout).until(attributeChanged(locator, attributeName, expectedValue));
    }

    default void waitUntilAttributeFrom(LazyLocator locator, String attributeName, String fromValue) {
        getWait().until(attributeChangedFrom(locator, attributeName, fromValue));
    }

    default void waitUntilIsInViewport(By by) {
        getWait().until(isInViewPort(by));
    }

    default void waitUntilIsInViewport(LazyLocator locator) {
        getWait().until(isInViewPort(locator));
    }

    default void waitUntilIsInViewport(By by, long timeout) {
        getWait(timeout).until(isInViewPort(by));
    }

    default void waitUntilIsInViewport(LazyLocator locator, long timeout) {
        getWait(timeout).until(isInViewPort(locator));
    }

    default void waitUntilAlertIsPresent() {
        getWait().until(alertIsPresent());
    }

    default void waitUntilAlertIsPresent(long timeout) {
        getWait(timeout).until(alertIsPresent());
    }

    default void waitUntilIsClickable(LazyLocator locator) {
        waitUntilEnabled(locator);
    }

    default void waitUntilIsClickable(LazyLocator locator, long timeout) {
        waitUntilEnabled(locator, timeout);
    }

    default void waitUntilNewWindowOpened(int beforeWindowsCount) {
        getWait().until(newWindowOpened(beforeWindowsCount));
    }

    default void waitUntilCurrentWindowClosed(int beforeWindowsCount) {
        getWait().until(currentWindowClosed(beforeWindowsCount));
    }
}
