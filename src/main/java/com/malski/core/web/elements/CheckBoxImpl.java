package com.malski.core.web.elements;

import com.malski.core.web.factory.LazyLocator;
import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;

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
}
