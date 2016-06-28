package com.malski.core.web.elements.impl;

import com.malski.core.web.elements.api.RadioButton;
import com.malski.core.web.factory.LazyLocator;
import com.malski.core.web.factory.Selector;
import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;

import static org.openqa.selenium.support.ui.ExpectedConditions.elementSelectionStateToBe;
import static org.openqa.selenium.support.ui.ExpectedConditions.elementToBeSelected;
import static com.malski.core.cucumber.TestContext.getBrowser;

public class RadioButtonImpl extends ElementImpl implements RadioButton {

    public RadioButtonImpl(By by, SearchContext context) {
        super(by, context);
    }

    public RadioButtonImpl(Selector selector, SearchContext context) {
        super(selector, context);
    }

    public RadioButtonImpl(LazyLocator locator) {
        super(locator);
    }

    public RadioButtonImpl(LazyLocator locator, WebElement element) {
        super(locator, element);
    }

    public RadioButtonImpl(By by, WebElement element) {
        super(by, element);
    }

    public void check() {
        getWrappedElement().click();
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
        getBrowser().getWait().until(elementToBeSelected(this));
    }

    @Override
    public void waitUntilChecked(long timeout) {
        getBrowser().getWait(timeout).until(elementToBeSelected(this));
    }

    @Override
    public void waitUntilUnchecked() {
        getBrowser().getWait().until(elementSelectionStateToBe(this, false));
    }

    @Override
    public void waitUntilUnchecked(long timeout) {
        getBrowser().getWait(timeout).until(elementSelectionStateToBe(this, false));
    }
}