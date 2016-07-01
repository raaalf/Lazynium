package com.malski.core.web.elements.impl;

import com.malski.core.web.base.LazySearchContext;
import com.malski.core.web.elements.api.Link;
import com.malski.core.web.factory.LazyLocator;
import com.malski.core.web.factory.Selector;
import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;

public class LinkImpl extends ElementImpl implements Link {

    public LinkImpl(By by, LazySearchContext context) {
        super(by, context);
    }

    public LinkImpl(Selector selector, LazySearchContext context) {
        super(selector, context);
    }

    public LinkImpl(LazyLocator locator) {
        super(locator);
    }

    public LinkImpl(LazyLocator locator, WebElement element) {
        super(locator, element);
    }

    public LinkImpl(By by, WebElement element) {
        super(by, element);
    }

    @Override
    public String getHref() {
        return this.getAttribute("href");
    }

    @Override
    public String getTarget() {
        return this.getAttribute("target");
    }
}
