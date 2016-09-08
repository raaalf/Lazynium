package com.malski.core.web.elements.api;

public interface ElementWait {

    void waitUntilPresent();

    void waitUntilPresent(long timeout);

    void waitUntilVisible();

    void waitUntilVisible(long timeout);

    void waitUntilDisappear();

    void waitUntilDisappear(long timeout);

    void waitUntilEnabled();

    void waitUntilEnabled(long timeout);

    void waitUntilDisabled();

    void waitUntilDisabled(long timeout);

    void waitUntilAttributeChange(String attributeName, String expectedValue);

    void waitUntilAttributeChange(String attributeName, String expectedValue, long timeout);

    void waitUntilIsInViewport();

    void waitUntilIsInViewport(long timeout);

    void waitUntilIsClickable();

    void waitUntilIsClickable(long timeout);
}
