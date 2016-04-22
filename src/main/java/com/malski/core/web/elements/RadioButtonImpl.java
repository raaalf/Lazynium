package com.malski.core.web.elements;

import com.malski.core.web.factory.LazyLocator;
import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;

public class RadioButtonImpl extends ElementImpl implements RadioButton {

    public RadioButtonImpl(By by, SearchContext context) {
        super(by, context);
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
}