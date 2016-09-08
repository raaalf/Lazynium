package com.malski.core.web.elements;

import com.malski.core.web.factory.LazyLocator;
import org.openqa.selenium.WebElement;

import static com.malski.core.utils.TestContext.getBrowser;
import static org.openqa.selenium.support.ui.ExpectedConditions.elementSelectionStateToBe;
import static org.openqa.selenium.support.ui.ExpectedConditions.elementToBeSelected;

public class CheckBox extends Element {

    public CheckBox(LazyLocator locator) {
        super(locator);
    }

    public CheckBox(LazyLocator locator, WebElement element) {
        super(locator, element);
    }

    public void select(boolean state) {
        if(state) {
            check();
        } else {
            uncheck();
        }
    }

    public void toggle() {
        getWrappedElement().click();
    }

    public void check() {
        if (!isChecked()) {
            toggle();
        }
    }

    public void uncheck() {
        if (isChecked()) {
            toggle();
        }
    }

    public boolean isChecked() {
        return getWrappedElement().isSelected();
    }

    public boolean isChecked(long timeout) {
        try {
            waitUntilChecked(timeout);
        } catch (Exception ignore) {
            return false;
        }
        return isChecked();
    }

    public void waitUntilChecked() {
        getBrowser().getWait().until(elementToBeSelected(this));
    }

    public void waitUntilChecked(long timeout) {
        getBrowser().getWait(timeout).until(elementToBeSelected(this));
    }

    public void waitUntilUnchecked() {
        getBrowser().getWait().until(elementSelectionStateToBe(this, false));
    }

    public void waitUntilUnchecked(long timeout) {
        getBrowser().getWait(timeout).until(elementSelectionStateToBe(this, false));
    }
}
