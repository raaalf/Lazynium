package com.malski.core.web.elements.impl;

import com.malski.core.web.base.LazySearchContext;
import com.malski.core.web.elements.api.Input;
import com.malski.core.web.factory.LazyLocator;
import com.malski.core.web.factory.Selector;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class InputImpl extends ElementImpl implements Input {

    public InputImpl(By by, LazySearchContext context) {
        super(by, context);
    }

    public InputImpl(Selector selector, LazySearchContext context) {
        super(selector, context);
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
