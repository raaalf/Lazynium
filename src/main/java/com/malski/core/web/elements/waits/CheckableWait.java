package com.malski.core.web.elements.waits;

import static com.malski.core.utils.TestContext.getBrowser;
import static org.openqa.selenium.support.ui.ExpectedConditions.elementSelectionStateToBe;
import static org.openqa.selenium.support.ui.ExpectedConditions.elementToBeSelected;

public interface CheckableWait extends ElementWait {

    default void waitUntilChecked() {
        getBrowser().getWait().until(elementToBeSelected(getWrappedElement()));
    }

    default void waitUntilChecked(long timeout) {
        getBrowser().getWait(timeout).until(elementToBeSelected(getWrappedElement()));
    }

    default void waitUntilUnchecked() {
        getBrowser().getWait().until(elementSelectionStateToBe(getWrappedElement(), false));
    }

    default void waitUntilUnchecked(long timeout) {
        getBrowser().getWait(timeout).until(elementSelectionStateToBe(getWrappedElement(), false));
    }
}
