package com.malski.core.web.elements;

public interface ElementsWait {
    void waitUntilAllPresent();

    void waitUntilAllPresent(long timeout);

    void waitUntilAnyPresent();

    void waitUntilAnyPresent(long timeout);

    void waitUntilAllVisible();

    void waitUntilAllVisible(long timeout);

    void waitUntilAnyVisible();

    void waitUntilAnyVisible(long timeout);

    void waitUntilAllDisappear();

    void waitUntilAllDisappear(long timeout);

    void waitUntilAnyDisappear();

    void waitUntilAnyDisappear(long timeout);

    void waitUntilAllEnabled();

    void waitUntilAllEnabled(long timeout);

    void waitUntilAnyEnabled();

    void waitUntilAnyEnabled(long timeout);

    void waitUntilAllDisabled();

    void waitUntilAllDisabled(long timeout);

    void waitUntilAnyDisabled();

    void waitUntilAnyDisabled(long timeout);

//    void waitUntilAllSelected();
//
//    void waitUntilAnySelected();
//
//    void waitUntilAllUnselected();
//
//    void waitUntilAnyUnselected();
}
