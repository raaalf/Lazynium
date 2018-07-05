package com.malski.lazynium.web.elements.waits;

import com.malski.lazynium.web.factory.LazyLocator;

import static com.malski.lazynium.utils.TestContext.browser;

public interface ElementWait {

    LazyLocator locator();

    default void waitUntilPresent() {
        browser().waitUntilPresent(locator());
    }

    default void waitUntilPresent(long timeout) {
        browser().waitUntilPresent(locator(), timeout);
    }

    default void waitUntilVisible() {
        browser().waitUntilVisible(locator());
    }

    default void waitUntilVisible(long timeout) {
        browser().waitUntilVisible(locator(), timeout);
    }

    default void waitUntilDisappear() {
        browser().waitUntilDisappear(locator());
    }

    default void waitUntilDisappear(long timeout) {
        browser().waitUntilDisappear(locator(), timeout);
    }

    default void waitUntilEnabled() {
        browser().waitUntilEnabled(locator());
    }

    default void waitUntilEnabled(long timeout) {
        browser().waitUntilEnabled(locator(), timeout);
    }

    default void waitUntilDisabled() {
        browser().waitUntilDisabled(locator());
    }

    default void waitUntilDisabled(long timeout) {
        browser().waitUntilDisabled(locator(), timeout);
    }

    default void waitUntilAttributeChange(String attributeName, String expectedValue) {
        browser().waitUntilAttributeChange(locator(), attributeName, expectedValue);
    }

    default void waitUntilAttributeChangeFrom(String attributeName, String startValue) {
        browser().waitUntilAttributeFrom(locator(), attributeName, startValue);
    }

    default void waitUntilAttributeChange(String attributeName, String expectedValue, long timeout) {
        browser().waitUntilAttributeChange(locator(), attributeName, expectedValue, timeout);
    }

    default void waitUntilIsInViewport() {
        browser().waitUntilIsInViewport(locator());
    }

    default void waitUntilIsInViewport(long timeout) {
        browser().waitUntilIsInViewport(locator(), timeout);
    }

    default void waitUntilIsClickable() {
        browser().waitUntilIsClickable(locator());
    }

    default void waitUntilIsClickable(long timeout) {
        browser().waitUntilIsClickable(locator(), timeout);
    }
}
