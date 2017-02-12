package com.malski.core.web.elements.states;

import com.malski.core.web.elements.waits.ElementWait;
import com.malski.core.web.factory.LazyLocator;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;

import static com.malski.core.utils.TestContext.getBrowser;

public interface ElementState extends ElementWait {

    boolean isStaleness();

    LazyLocator getLocator();

    WebElement getWrappedElement();

    default boolean isDisplayed(long timeout) {
        try {
            waitUntilIsInViewport(timeout);
        } catch (Exception ignore) {
            return false;
        }
        return getWrappedElement().isDisplayed();
    }

    default boolean isVisible() {
        return getWrappedElement().isDisplayed();
    }

    default boolean isVisible(long timeout) {
        try {
            waitUntilVisible(timeout);
        } catch (Exception ignore) {
            return false;
        }
        return getWrappedElement().isDisplayed();
    }

    default boolean isPresent() {
        try {
            return getLocator().findElement() != null;
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
        return getWrappedElement().equals(getBrowser().switchTo().activeElement());
    }

    default boolean hasFocus(long timeout) {
        try {
            waitUntilVisible(timeout);
        } catch (Exception ignore) {
            return false;
        }
        return getWrappedElement().equals(getBrowser().switchTo().activeElement());
    }

    default boolean isInViewport() {
        Dimension elemDim = getWrappedElement().getSize();
        Point point = getWrappedElement().getLocation();

        int elemY = elemDim.getHeight() + point.getY();
        long browserHeight = getBrowser().jsExecutor().getJsClientHeight();
        long scrollHeight = getBrowser().jsExecutor().getScrollHeight();

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
