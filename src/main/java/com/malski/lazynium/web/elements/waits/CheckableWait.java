package com.malski.lazynium.web.elements.waits;

import static com.malski.lazynium.utils.TestContext.browser;
import static org.openqa.selenium.support.ui.ExpectedConditions.elementSelectionStateToBe;
import static org.openqa.selenium.support.ui.ExpectedConditions.elementToBeSelected;

public interface CheckableWait extends ElementWait {

    default void waitUntilChecked() {
        browser().getWait().until(elementToBeSelected(getWrappedElement()));
    }

    default void waitUntilChecked(long timeout) {
        browser().getWait(timeout).until(elementToBeSelected(getWrappedElement()));
    }

    default void waitUntilUnchecked() {
        browser().getWait().until(elementSelectionStateToBe(getWrappedElement(), false));
    }

    default void waitUntilUnchecked(long timeout) {
        browser().getWait(timeout).until(elementSelectionStateToBe(getWrappedElement(), false));
    }
}
