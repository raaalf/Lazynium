package com.malski.core.web.elements.waits;

import com.malski.core.web.conditions.WaitConditions;
import com.malski.core.web.elements.Element;
import com.malski.core.web.factory.LazyLocator;

import java.util.List;

import static com.malski.core.utils.TestContext.getBrowser;
import static com.malski.core.web.conditions.WaitConditions.*;
import static org.openqa.selenium.support.ui.ExpectedConditions.not;

public interface ListWait<E extends Element> {

    LazyLocator getLocator();

    List<E> getWrappedElements();

    default void waitUntilAllPresent() {
        getBrowser().getWait().until(presenceOfAllElementsLocatedBy(getLocator()));
    }

    default void waitUntilAnyPresent() {
        getBrowser().getWait().until(presenceOfElementLocated(getLocator()));
    }

    default void waitUntilAllVisible() {
        getBrowser().getWait().until(visibilityOfAllElementsLocatedBy(getLocator()));
    }

    default void waitUntilAnyVisible() {
        getBrowser().getWait().until(visibilityOfElementLocated(getLocator()));
    }

    @SuppressWarnings("unchecked")
    default void waitUntilAllDisappear() {
        getBrowser().getWait().until(WaitConditions.invisibilityOfAllElements(getWrappedElements()));
    }

    default void waitUntilAnyDisappear() {
        getBrowser().getWait().until(invisibilityOfElementLocated(getLocator()));
    }

    default void waitUntilAllEnabled() {
        getBrowser().getWait().until(WaitConditions.elementsToBeClickable(getLocator()));
    }

    default void waitUntilAnyEnabled() {
        getBrowser().getWait().until(elementToBeClickable(getLocator()));
    }

    default void waitUntilAllDisabled() {
        getBrowser().getWait().until(not(WaitConditions.elementsToBeClickable(getLocator())));
    }

    default void waitUntilAnyDisabled() {
        getBrowser().getWait().until(not(elementToBeClickable(getLocator())));
    }

//    void waitUntilAllSelected();
//
//    void waitUntilAnySelected();
//
//    void waitUntilAllUnselected();
//
//    void waitUntilAnyUnselected();
}
