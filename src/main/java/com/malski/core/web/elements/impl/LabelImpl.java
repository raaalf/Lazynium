package com.malski.core.web.elements.impl;

import com.malski.core.web.elements.api.Label;
import com.malski.core.web.factory.LazyLocator;
import com.malski.core.web.factory.Selector;
import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;

public class LabelImpl extends ElementImpl implements Label {

    public LabelImpl(By by, SearchContext context) {
        super(by, context);
    }

    public LabelImpl(Selector selector, SearchContext context) {
        super(selector, context);
    }

    public LabelImpl(LazyLocator locator) {
        super(locator);
    }

    public LabelImpl(LazyLocator locator, WebElement element) {
        super(locator, element);
    }

    public LabelImpl(By by, WebElement element) {
        super(by, element);
    }

    @Override
    public String getFor() {
        return this.getAttribute("for");
    }
}
