package com.malski.core.web.elements;

import com.malski.core.web.factory.LazyLocator;
import org.openqa.selenium.WebElement;

public class Image extends Element {

    public Image(LazyLocator locator) {
        super(locator);
    }

    public Image(LazyLocator locator, WebElement element) {
        super(locator, element);
    }

    public String alt() {
        return this.getAttribute("alt");
    }

    public String src() {
        return this.getAttribute("src");
    }
}
