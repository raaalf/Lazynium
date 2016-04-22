package com.malski.core.web.elements;

import com.malski.core.web.factory.LazyLocator;
import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;

public class ImageImpl extends ElementImpl implements Image {

    public ImageImpl(By by, SearchContext context) {
        super(by, context);
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
