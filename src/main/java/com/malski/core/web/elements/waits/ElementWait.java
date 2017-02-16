package com.malski.core.web.elements.waits;

import com.malski.core.web.factory.LazyLocator;

import static com.malski.core.utils.TestContext.getBrowser;

public interface ElementWait {

    LazyLocator getLocator();

    default void waitUntilPresent() {
        getBrowser().waitUntilPresent(getLocator());
    }

    default void waitUntilPresent(long timeout) {
        getBrowser().waitUntilPresent(getLocator(), timeout);
    }

    default void waitUntilVisible() {
        getBrowser().waitUntilVisible(getLocator());
    }

    default void waitUntilVisible(long timeout) {
        getBrowser().waitUntilVisible(getLocator(), timeout);
    }

    default void waitUntilDisappear() {
        getBrowser().waitUntilDisappear(getLocator());
    }

    default void waitUntilDisappear(long timeout) {
        getBrowser().waitUntilDisappear(getLocator(), timeout);
    }

    default void waitUntilEnabled() {
        getBrowser().waitUntilEnabled(getLocator());
    }

    default void waitUntilEnabled(long timeout) {
        getBrowser().waitUntilEnabled(getLocator(), timeout);
    }

    default void waitUntilDisabled() {
        getBrowser().waitUntilDisabled(getLocator());
    }

    default void waitUntilDisabled(long timeout) {
        getBrowser().waitUntilDisabled(getLocator(), timeout);
    }

    default void waitUntilAttributeChange(String attributeName, String expectedValue) {
        getBrowser().waitUntilAttributeChange(getLocator(), attributeName, expectedValue);
    }

    default void waitUntilAttributeChangeFrom(String attributeName, String startValue) {
        getBrowser().waitUntilAttributeFrom(getLocator(), attributeName, startValue);
    }

    default void waitUntilAttributeChange(String attributeName, String expectedValue, long timeout) {
        getBrowser().waitUntilAttributeChange(getLocator(), attributeName, expectedValue, timeout);
    }

    default void waitUntilIsInViewport() {
        getBrowser().waitUntilIsInViewport(getLocator());
    }

    default void waitUntilIsInViewport(long timeout) {
        getBrowser().waitUntilIsInViewport(getLocator(), timeout);
    }

    default void waitUntilIsClickable() {
        getBrowser().waitUntilIsClickable(getLocator());
    }

    default void waitUntilIsClickable(long timeout) {
        getBrowser().waitUntilIsClickable(getLocator(), timeout);
    }
}
