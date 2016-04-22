package com.malski.core.web.elements;

import com.malski.core.web.factory.LazyLocator;
import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;

public class InputImpl extends ElementImpl implements Input {

    public InputImpl(By by, SearchContext context) {
        super(by, context);
    }

    public InputImpl(LazyLocator locator) {
        super(locator);
    }

    public InputImpl(LazyLocator locator, WebElement element) {
        super(locator, element);
    }

    public InputImpl(By by, WebElement element) {
        super(by, element);
    }

    @Override
    public void fill(String text) {
        this.clear();
        this.sendKeys(text);
    }
}
