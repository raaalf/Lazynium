package com.malski.lazynium.web.elements.waits;

import com.malski.lazynium.web.conditions.WaitConditions;
import com.malski.lazynium.web.elements.Element;
import com.malski.lazynium.web.factory.LazyLocator;

import java.util.List;

import static com.malski.lazynium.utils.TestContext.browser;
import static com.malski.lazynium.web.conditions.WaitConditions.*;
import static org.openqa.selenium.support.ui.ExpectedConditions.not;

public interface ListWait<E extends Element> {

    LazyLocator getLocator();

    List<E> getWrappedElements();

    default void waitUntilAllPresent() {
        browser().getWait().until(presenceOfAllElementsLocatedBy(getLocator()));
    }

    default void waitUntilAnyPresent() {
        browser().getWait().until(presenceOfElementLocated(getLocator()));
    }

    default void waitUntilAllVisible() {
        browser().getWait().until(visibilityOfAllElementsLocatedBy(getLocator()));
    }

    default void waitUntilAnyVisible() {
        browser().getWait().until(visibilityOfElementLocated(getLocator()));
    }

    @SuppressWarnings("unchecked")
    default void waitUntilAllDisappear() {
        browser().getWait().until(WaitConditions.invisibilityOfAllElements(getWrappedElements()));
    }

    default void waitUntilAnyDisappear() {
        browser().getWait().until(invisibilityOfElementLocated(getLocator()));
    }

    default void waitUntilAllEnabled() {
        browser().getWait().until(WaitConditions.elementsToBeClickable(getLocator()));
    }

    default void waitUntilAnyEnabled() {
        browser().getWait().until(elementToBeClickable(getLocator()));
    }

    default void waitUntilAllDisabled() {
        browser().getWait().until(not(WaitConditions.elementsToBeClickable(getLocator())));
    }

    default void waitUntilAnyDisabled() {
        browser().getWait().until(not(elementToBeClickable(getLocator())));
    }

//    void waitUntilAllSelected();
//
//    void waitUntilAnySelected();
//
//    void waitUntilAllUnselected();
//
//    void waitUntilAnyUnselected();
}
