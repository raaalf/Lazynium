package com.malski.core.web.elements;

import com.malski.core.web.factory.LazyLocator;
import org.openqa.selenium.WebElement;

import static com.malski.core.utils.TestContext.getBrowser;
import static org.openqa.selenium.support.ui.ExpectedConditions.elementSelectionStateToBe;
import static org.openqa.selenium.support.ui.ExpectedConditions.elementToBeSelected;

public class RadioButton extends Element {

    public RadioButton(LazyLocator locator) {
        super(locator);
    }

    public RadioButton(LazyLocator locator, WebElement element) {
        super(locator, element);
    }

    public void check() {
        getWrappedElement().click();
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