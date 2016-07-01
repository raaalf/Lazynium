package com.malski.core.web.elements.impl;

import com.malski.core.web.base.LazySearchContext;
import com.malski.core.web.elements.api.Image;
import com.malski.core.web.factory.LazyLocator;
import com.malski.core.web.factory.Selector;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class ImageImpl extends ElementImpl implements Image {

    public ImageImpl(By by, LazySearchContext context) {
        super(by, context);
    }

    public ImageImpl(Selector selector, LazySearchContext context) {
        super(selector, context);
    }

    public ImageImpl(LazyLocator locator) {
        super(locator);
    }

    public ImageImpl(LazyLocator locator, WebElement element) {
        super(locator, element);
    }

    public ImageImpl(By by, WebElement element) {
        super(by, element);
    }

    @Override
    public String getAlt() {
        return this.getAttribute("alt");
    }

    @Override
    public String getSrc() {
        return this.getAttribute("src");
    }
}
