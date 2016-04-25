package com.malski.core.web.elements;

import com.malski.core.cucumber.TestContext;
import com.malski.core.web.factory.LazyLocator;
import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;

import static org.openqa.selenium.support.ui.ExpectedConditions.elementSelectionStateToBe;
import static org.openqa.selenium.support.ui.ExpectedConditions.elementToBeSelected;

public class CheckBoxImpl extends ElementImpl implements CheckBox {

    public CheckBoxImpl(By by, SearchContext context) {
        super(by, context);
    }

    public CheckBoxImpl(LazyLocator locator) {
        super(locator);
    }

    public CheckBoxImpl(LazyLocator locator, WebElement element) {
        super(locator, element);
    }

    public CheckBoxImpl(By by, WebElement element) {
        super(by, element);
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

    @Override
    public boolean isChecked(long timeout) {
        try {
            waitUntilChecked(timeout);
        } catch (Exception ignore) {
            return false;
        }
        return isChecked();
    }

    @Override
    public void waitUntilChecked() {
        TestContext.getBrowser().getWait().until(elementToBeSelected(this));
    }

    @Override
    public void waitUntilChecked(long timeout) {
        TestContext.getBrowser().getWait(timeout).until(elementToBeSelected(this));
    }

    @Override
    public void waitUntilUnchecked() {
        TestContext.getBrowser().getWait().until(elementSelectionStateToBe(this, false));
    }

    @Override
    public void waitUntilUnchecked(long timeout) {
        TestContext.getBrowser().getWait(timeout).until(elementSelectionStateToBe(this, false));
    }
}
