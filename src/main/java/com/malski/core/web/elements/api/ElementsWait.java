package com.malski.core.web.elements.api;

public interface ElementsWait {
    void waitUntilAllPresent();

    void waitUntilAnyPresent();

    void waitUntilAllVisible();

    void waitUntilAnyVisible();

    void waitUntilAllDisappear();

    void waitUntilAnyDisappear();

    void waitUntilAllEnabled();

    void waitUntilAnyEnabled();

    void waitUntilAllDisabled();

    void waitUntilAnyDisabled();

//    void waitUntilAllSelected();
//
//    void waitUntilAnySelected();
//
//    void waitUntilAllUnselected();
//
//    void waitUntilAnyUnselected();
}
