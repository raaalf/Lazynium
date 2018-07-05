package com.malski.lazynium.web.elements.states;

import com.malski.lazynium.web.elements.waits.ElementWait;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.Point;
import org.openqa.selenium.internal.WrapsElement;

import static com.malski.lazynium.utils.TestContext.browser;

public interface ElementState extends ElementWait, WrapsElement {

    boolean isStaleness();

    default boolean isDisplayed() {
        return getWrappedElement().isDisplayed();
    }

    default boolean isDisplayed(long timeout) {
        try {
            waitUntilIsInViewport(timeout);
        } catch (Exception ignore) {
            return false;
        }
        return getWrappedElement().isDisplayed();
    }

    default boolean isVisible() {
        return isPresent() && getWrappedElement().isDisplayed();
    }

    default boolean isVisible(long timeout) {
        try {
            waitUntilVisible(timeout);
        } catch (Exception ignore) {
            return false;
        }
        return isPresent() && getWrappedElement().isDisplayed();
    }

    default boolean isPresent() {
        try {
            return locator().findElement() != null;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    default boolean isPresent(long timeout) {
        try {
            waitUntilPresent(timeout);
        } catch (Exception ignore) {
            return false;
        }
        return isPresent();
    }

    default boolean isEnabled(long timeout) {
        try {
            waitUntilEnabled(timeout);
        } catch (Exception ignore) {
            return false;
        }
        return getWrappedElement().isEnabled();
    }

    default boolean hasFocus() {
        return getWrappedElement().equals(browser().switchTo().activeElement());
    }

    default boolean hasFocus(long timeout) {
        try {
            waitUntilVisible(timeout);
        } catch (Exception ignore) {
            return false;
        }
        return getWrappedElement().equals(browser().switchTo().activeElement());
    }

    default boolean isInViewport() {
        Dimension elemDim = getWrappedElement().getSize();
        Point point = getWrappedElement().getLocation();

        int elemY = elemDim.getHeight() + point.getY();
        long browserHeight = browser().jsExecutor().getJsClientHeight();
        long scrollHeight = browser().jsExecutor().getScrollHeight();

        return elemY >= scrollHeight && elemY <= scrollHeight + browserHeight;
    }

    default boolean isInViewport(long timeout) {
        try {
            waitUntilIsInViewport(timeout);
        } catch (Exception ignore) {
            return false;
        }
        return isInViewport();
    }
}
