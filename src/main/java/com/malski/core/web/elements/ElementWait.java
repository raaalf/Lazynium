package com.malski.core.web.elements;

public interface ElementWait {

    void waitUntilPresent();

    void waitUntilVisible();

    void waitUntilDisappear();

    void waitUntilEnabled();

    void waitUntilDisabled();
}
