package com.malski.lazynium.web.elements;

import com.malski.lazynium.web.factory.LazyLocator;
import org.openqa.selenium.WebElement;

public class Link extends Element {

    public Link(LazyLocator locator) {
        super(locator);
    }

    public Link(LazyLocator locator, WebElement element) {
        super(locator, element);
    }

    public String href() {
        return this.getAttribute("href");
    }

    public String target() {
        return this.getAttribute("target");
    }
}
